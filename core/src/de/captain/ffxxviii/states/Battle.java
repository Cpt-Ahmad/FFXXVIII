package de.captain.ffxxviii.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.components.CombatInfo;
import de.captain.ffxxviii.entity.components.Equipment;
import de.captain.ffxxviii.entity.presets.Goblin;
import de.captain.ffxxviii.entity.presets.SelectionArrow;
import de.captain.ffxxviii.entity.systems.TextureRenderer;
import de.captain.ffxxviii.entityold.type.AttackType;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.StateStacker;
import de.captain.ffxxviii.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Deprecated
public class Battle extends State
{
    private boolean m_shouldUpdateBattlefieldPosition = true;
    private boolean m_isBattleInWaitingMode           = false;

    private int m_cursor;
    private int m_battlingEntityCount;
    private int m_playerCounter;
    private int m_enemyCounter;

    private final List<Entity> m_entities = new ArrayList<>();
    private final List<Entity> m_enemiesKilled;

    private final Entity m_arrow;
    private final Entity m_player;
    private       Entity m_currentEntity;

    private Engine m_engine = new Engine();

    Battle(StateStacker stateStacker,
           Entity player)
    {
        super(stateStacker);
        de.captain.ffxxviii.entity.components.PlayerTeam
                playerTeam = player.getComponent(de.captain.ffxxviii.entity.components.PlayerTeam.class);
        if (playerTeam != null && !playerTeam.isEmpty())
        {
            m_playerCounter = playerTeam.size();

            m_enemiesKilled = new ArrayList<>(6);
            //m_entities = m_engine.getEntityList();

            for (int i = 0; i < playerTeam.all.length; i++)
            {
                if (playerTeam.all[i] == null) continue;
                de.captain.ffxxviii.entity.components.RenderPosition renderPos = playerTeam.all[i].getComponent(
                        de.captain.ffxxviii.entity.components.RenderPosition.class);
                CombatInfo cInfo = playerTeam.all[i].getComponent(CombatInfo.class);
                Equipment  equip = playerTeam.all[i].getComponent(Equipment.class);

                cInfo.applyEquipmentToStats(equip);
                renderPos.set(50 + (cInfo.isFrontLine ? 75 : 0), 100 + 75 * i);
            }

            m_player = player;

            m_engine.addSystem(new TextureRenderer());

            m_enemyCounter = 4;

            m_arrow = new SelectionArrow();
            m_engine.addEntity(m_arrow);
            //m_engine.addEntities(playerTeam.all);
            //m_engine.addEntities(createEnemyTeam(m_enemyCounter));
        } else
        {
            throw new IllegalArgumentException("player does not have a team to battle");
        }
    }

    @Override
    public void update(float delta)
    {
        m_engine.update(delta);

        // Update the battlefield position of the entities, if necessary
        if (m_shouldUpdateBattlefieldPosition)
        {
            setBattlefieldPosition(m_entities);
            if (m_cursor >= m_battlingEntityCount)
            {
                m_cursor = m_battlingEntityCount - 1;
            }
        }

        for (Entity entity : m_entities)
        {
            CombatInfo attacker = entity.getComponent(CombatInfo.class);

            if (attacker != null && attacker.isAlive)
            {
                // Sets the render position of the selection arrow
                if (m_cursor == attacker.battlefieldPosition)
                {
                    de.captain.ffxxviii.entity.components.RenderPosition
                            renderPos = entity.getComponent(de.captain.ffxxviii.entity.components.RenderPosition.class);
                    m_arrow.getComponent(de.captain.ffxxviii.entity.components.RenderPosition.class)
                           .set(renderPos.x - 32f, renderPos.y);
                }

                // is player currently selecting an attack (everything will be frozen in that case)
                if (!m_isBattleInWaitingMode)
                {
                    // Does an entityold have an attack selected and enough stamina to use it
                    if (attacker.isReadyToAttack())
                    {
                        Entity defender = getEntityByBattlefieldPosition(attacker.getTarget());
                        if (defender == null) // is the targeted entityold already dead?
                        {
                            int newTarget = chooseNewTarget(attacker, attacker.getTarget());
                            defender = getEntityByBattlefieldPosition(newTarget);
                            if (defender == null) // no entityold in the opposing team is alive
                            {
                                attacker.setAttackFinished();
                                continue;
                            }
                        }
                        attack(entity, defender);
                    } else if (!attacker.isAttackSelected()) // is an attack selected for this entityold
                    {
                        // Is the entityold an enemy -> select an attack and target
                        if (attacker.isEnemy)
                        {
                            selectEnemyAttackAgainstPlayer(m_entities, entity);
                            Log.debug(Log.Logger.BATTLE, "Enemy chose attack");
                        } else // if its a player, set the battle to waiting mode
                        {
                            Log.debug(Log.Logger.BATTLE, "Player Turn");
                            m_currentEntity = entity;
                            m_isBattleInWaitingMode = true;
                        }
                    } else // if not in waiting mode, increase stamina of the entityold
                    {
                        attacker.stamina += attacker.staminaPerSec;
                    }
                }
            }
        }

        // Remove all dead enemies from the active entityold list and add them to the dead enemy list
        // Additionally reduce the appropriate counter
        for (Iterator<Entity> iterator = m_entities.iterator(); iterator.hasNext(); )
        {
            Entity     entity = iterator.next();
            CombatInfo cInfo  = entity.getComponent(CombatInfo.class);

            if (cInfo != null && !cInfo.isAlive)
            {
                if (cInfo.isEnemy)
                {
                    m_enemiesKilled.add(entity);
                    String name = entity.getComponent(de.captain.ffxxviii.entity.components.Name.class).name;
                    Log.debug(Log.Logger.BATTLE, name + " died.");
                    iterator.remove();
                    m_enemyCounter--;
                } else
                {
                    m_playerCounter--;
                }
            }
        }

        // All enemies are defeated
        if (m_enemyCounter == 0)
        {
            int totalExp = 0;
            de.captain.ffxxviii.entity.components.Inventory playerInv = m_player.getComponent(
                    de.captain.ffxxviii.entity.components.Inventory.class);
            // Get the total exp from the battle and give the player all the loot
            for (Entity entity : m_enemiesKilled)
            {
                CombatInfo cInfo = entity.getComponent(CombatInfo.class);
                totalExp += cInfo.experiencePoints;
                de.captain.ffxxviii.entity.components.Inventory
                        loot = entity.getComponent(de.captain.ffxxviii.entity.components.Inventory.class);
                if (loot != null)
                {
                    Log.debug(Log.Logger.BATTLE, "Loot: " + loot.toString());
                    playerInv.add(loot);
                }
            }
            // Add the exp to the player characters
            for (Entity entity : m_entities)
            {
                CombatInfo cInfo = entity.getComponent(CombatInfo.class);
                if (cInfo != null && cInfo.isAlive && !cInfo.isEnemy)
                {
                    cInfo.experiencePoints += (totalExp / m_playerCounter);
                }
            }
            m_stateStacker.pop();
        }

        m_camera.update();
    }

    public void render()
    {
        Assets.getSpriteBatch().setProjectionMatrix(m_camera.combined);
        ShapeRenderer shapeRenderer = Assets.getShapeRenderer();

        shapeRenderer.setProjectionMatrix(m_camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(0, 0, m_camera.viewportWidth, m_camera.viewportHeight);
        shapeRenderer.end();
        //m_engine.render(m_batch, shapeRenderer);
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new BattleInputProcessor()));
    }

    @Override
    public void dispose()
    {
    }


    private void attack(Entity attackerEntity, Entity defenderEntity)
    {
        if (attackerEntity == null)
        {
            throw new IllegalArgumentException("The attacking entityold cannot be null");
        }
        if (defenderEntity == null)
        {
            throw new IllegalArgumentException("The defending entityold cannot be null");
        }

        CombatInfo attacker = attackerEntity.getComponent(CombatInfo.class);
        CombatInfo defender = defenderEntity.getComponent(CombatInfo.class);
        int damageTaken =
                defender.damage(attacker.getAttackDamage() * attacker.strength, attacker.getAttackElement());
        attacker.setAttackFinished();

        de.captain.ffxxviii.entity.components.Name
                attackerName = attackerEntity.getComponent(
                de.captain.ffxxviii.entity.components.Name.class);
        de.captain.ffxxviii.entity.components.Name defenderName = defenderEntity.getComponent(
                de.captain.ffxxviii.entity.components.Name.class);

        String attName = attackerName == null ? "Attacker" : attackerName.name;
        String defName = defenderName == null ? "Defender" : defenderName.name;

        Log.debug(Log.Logger.BATTLE,
                  attName + " attacked " + defName + " and inflicted " + damageTaken + " damage.");

        if (!defender.isAlive)
        {
            de.captain.ffxxviii.entity.components.Name
                    nameContainer = defenderEntity.getComponent(de.captain.ffxxviii.entity.components.Name.class);
            String name = nameContainer == null ? "Enemy" : nameContainer.name;
            Log.debug(Log.Logger.BATTLE, name + " died");

            m_shouldUpdateBattlefieldPosition = true;
        }
    }

    private void selectEnemyAttackAgainstPlayer(List<Entity> entities, Entity attacker)
    {
        CombatInfo enemy = attacker.getComponent(CombatInfo.class);
        if (enemy == null) return;

        for (Entity entity : entities)
        {
            CombatInfo player = entity.getComponent(CombatInfo.class);
            if (player != null && !player.isEnemy)
            {
                int defenderIndex = player.battlefieldPosition;
                enemy.setAttack(AttackType.HEAVY_ATTACK, defenderIndex);
                return;
            }
        }
    }

    private Entity getEntityByBattlefieldPosition(int index)
    {
        CombatInfo cInfo;
        for (Entity entity : m_entities)
        {
            cInfo = entity.getComponent(CombatInfo.class);
            if (cInfo != null && cInfo.battlefieldPosition == index)
            {
                return entity;
            }
        }
        return null;
    }

    private void setBattlefieldPosition(List<Entity> entities)
    {
        entities.sort(new EntityBattlefieldPositionComparator());
        int index = 0;
        for (Entity entity : entities)
        {
            CombatInfo info = entity.getComponent(CombatInfo.class);
            if (info != null)
            {
                info.battlefieldPosition = index++;
            }
        }
        m_shouldUpdateBattlefieldPosition = false;
        m_battlingEntityCount = index;
    }

    private int chooseNewTarget(CombatInfo attacker, int target)
    {
        for (Entity entity : m_entities)
        {
            CombatInfo defender = entity.getComponent(CombatInfo.class);
            if (defender != null)
            {
                if ((attacker.isEnemy && !defender.isEnemy) || (!attacker.isEnemy && defender.isEnemy))
                {
                    return defender.battlefieldPosition;
                }
            }
        }
        return -1;
    }

    private class EntityBattlefieldPositionComparator implements Comparator<Entity>
    {
        @Override
        public int compare(Entity o1, Entity o2)
        {
            if (o1.equals(o2))
            {
                return 0;
            }
            de.captain.ffxxviii.entity.components.RenderPosition
                    pos1 = o1.getComponent(
                    de.captain.ffxxviii.entity.components.RenderPosition.class);
            de.captain.ffxxviii.entity.components.RenderPosition pos2 = o2.getComponent(
                    de.captain.ffxxviii.entity.components.RenderPosition.class);

            if (pos1 == null && pos2 == null)
            {
                return 0;
            } else if (pos1 != null && pos2 == null)
            {
                return 1;
            } else if (pos1 == null)
            {
                return -1;
            } else
            {
                if (pos1.x < pos2.x)
                {
                    return -1;
                } else if (pos1.x > pos2.x)
                {
                    return 1;
                } else
                {
                    return Float.compare(pos1.y, pos2.y) * -1;
                }
            }
        }
    }

    private List<Entity> createEnemyTeam(int sizeOfTeam)
    {
        if (sizeOfTeam < 1 || sizeOfTeam > 6)
        {
            throw new IllegalArgumentException("Enemy team size has to be between 1 and 6");
        }

        List<Entity> enemyTeam = new ArrayList<>();

        // TODO add implementation of enemy team generator
        for (int i = 0; i < sizeOfTeam; i++)
        {
            float   x, y;
            boolean isFrontLine;

            x = 400 + (i % 2) * 75;
            y = 100 + (i % 3) * 75;
            isFrontLine = i % 2 == 0;

            Goblin goblin = new Goblin(x, y, isFrontLine);
            enemyTeam.add(goblin);
        }

        return enemyTeam;
    }

    private class BattleInputProcessor extends InputAdapter
    {
        @Override
        public boolean keyDown(int keycode)
        {
            // move the selection cursor around
            if (keycode == Input.Keys.W)
            {
                m_cursor--;
                if (m_cursor < 0)
                {
                    m_cursor = m_battlingEntityCount - 1;
                }
                return true;
            } else if (keycode == Input.Keys.S)
            {
                m_cursor++;
                if (m_cursor == m_battlingEntityCount)
                {
                    m_cursor = 0;
                }
                return true;
            }

            // a player character has to select an action
            if (keycode == Input.Keys.SPACE)
            {
                if (m_currentEntity != null)
                {
                    Log.debug(Log.Logger.BATTLE, "Player chose attack");
                    CombatInfo cInfoAttacker = m_currentEntity.getComponent(CombatInfo.class);
                    cInfoAttacker.setAttack(AttackType.BASIC_ATTACK, m_cursor);
                    m_currentEntity = null;
                    m_isBattleInWaitingMode = false;
                }
                return true;
            }

            return false;
        }
    }
}
