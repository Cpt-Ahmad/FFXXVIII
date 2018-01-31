package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.entity.presets.BattleArrow;
import de.captain.ffxxviii.entity.presets.Goblin;
import de.captain.ffxxviii.entity.systems.TextureRenderer;
import de.captain.ffxxviii.entity.type.AttackType;
import de.captain.ffxxviii.main.StateStacker;
import de.captain.ffxxviii.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Battle extends State
{
    private boolean m_shouldUpdateBattlefieldPosition = true;
    private boolean m_isBattleInWaitingMode           = false;

    private int m_cursor;
    private int m_battlingEntityCount;
    private int m_playerCounter;
    private int m_enemyCounter;

    private final List<Entity> m_entities;
    private final List<Entity> m_enemiesKilled;

    private final Entity m_arrow;
    private final Entity m_player;
    private       Entity m_currentEntity;

    private EntityHandler m_entityHandler = new EntityHandler();

    Battle(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker, List<Entity> playerTeam,
           Entity player)
    {
        super(batch, shapeRenderer, stateStacker);
        if (playerTeam.size() > 3 || playerTeam.size() == 0)
        {
            throw new IllegalArgumentException(
                    "Player team can have up to 3 entities and has to be non-zero (" + playerTeam.size() + " in it)");
        }

        m_playerCounter = playerTeam.size();

        m_enemiesKilled = new ArrayList<>(6);
        m_entities = m_entityHandler.getEntityList();

        for (int i = 0; i < playerTeam.size(); i++)
        {
            Entity         entity    = playerTeam.get(i);
            RenderPosition renderPos = entity.getComponent(RenderPosition.class);
            CombatInfo     cInfo     = entity.getComponent(CombatInfo.class);

            renderPos.set(50 + (cInfo.isFrontLine ? 75 : 0), 100 + 75 * i);
        }

        m_player = player;

        m_entityHandler.addSystem(new TextureRenderer());

        m_enemyCounter = 2;

        m_arrow = new BattleArrow();
        m_entityHandler.addEntity(m_arrow);
        m_entityHandler.addEntities(playerTeam);
        m_entityHandler.addEntities(createEnemyTeam(m_enemyCounter));
    }

    @Override
    public void update(float delta)
    {
        m_entityHandler.update();

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
                    RenderPosition renderPos = entity.getComponent(RenderPosition.class);
                    m_arrow.getComponent(RenderPosition.class).set(renderPos.x - 32f, renderPos.y);
                }

                // is player currently selecting an attack (everything will be frozen in that case)
                if (!m_isBattleInWaitingMode)
                {
                    // Does an entity have an attack selected and enough stamina to use it
                    if (attacker.isReadyToAttack())
                    {
                        attack(entity, getEntityByBattlefieldPosition(attacker.getTarget()));
                    } else if (!attacker.isAttackSelected()) // is an attack selected for this entity
                    {
                        // Is the entity an enemy -> select an attack and target
                        if (entity.hasComponent(Enemy.class))
                        {
                            selectEnemyAttackAgainstPlayer(m_entities, entity);
                            Log.debug(Log.Logger.BATTLE, "Enemy chose attack");
                        } else if (entity
                                .hasComponent(PlayerTeam.class)) // if its a player, set the battle to waiting mode
                        {
                            Log.debug(Log.Logger.BATTLE, "Player Turn");
                            m_currentEntity = entity;
                            m_isBattleInWaitingMode = true;
                        }
                    } else // if not in waiting mode, increase stamina of the entity
                    {
                        attacker.stamina += attacker.staminaPerSec;
                    }
                }
            }
        }

        for (Iterator<Entity> iterator = m_entities.iterator(); iterator.hasNext(); )
        {
            Entity     entity = iterator.next();
            CombatInfo cInfo  = entity.getComponent(CombatInfo.class);

            if (cInfo == null)
            {
                continue;
            }

            if (!cInfo.isAlive)
            {
                if (entity.hasComponent(Enemy.class))
                {
                    m_enemiesKilled.add(entity);
                    String name = entity.getComponent(Name.class).name;
                    Log.debug(Log.Logger.BATTLE, name + " died.");
                    iterator.remove();
                    m_enemyCounter--;
                } else if (entity.hasComponent(PlayerTeam.class))
                {
                    m_playerCounter--;
                }
            }
        }

        if (m_enemyCounter == 0)
        {
            int       totalExp  = 0;
            Inventory playerInv = m_player.getComponent(Inventory.class);
            // Get the total exp from the battle and give the player all the loot
            for (Entity entity : m_enemiesKilled)
            {
                CombatInfo cInfo = entity.getComponent(CombatInfo.class);
                totalExp += cInfo.experiencePoints;
                Inventory loot = entity.getComponent(Inventory.class);
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
                if (entity.hasComponent(PlayerTeam.class) && cInfo != null && cInfo.isAlive)
                {
                    cInfo.experiencePoints += (totalExp / m_playerCounter);
                }
            }
            m_stateStacker.pop();
        }

        m_camera.update();
    }

    @Override
    public void render()
    {
        m_batch.setProjectionMatrix(m_camera.combined);
        m_shapeRenderer.setProjectionMatrix(m_camera.combined);

        m_entityHandler.render(m_batch, m_shapeRenderer);
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
            throw new IllegalArgumentException("The attacking entity cannot be null");
        }
        if (defenderEntity == null)
        {
            throw new IllegalArgumentException("The defending entity cannot be null");
        }

        CombatInfo attacker    = attackerEntity.getComponent(CombatInfo.class);
        CombatInfo defender    = defenderEntity.getComponent(CombatInfo.class);
        int        damageTaken = defender.damage(attacker.getAttackDamage(), attacker.getAttackElement());
        attacker.setAttackFinished();

        Name attackerName = attackerEntity.getComponent(Name.class);
        Name defenderName = defenderEntity.getComponent(Name.class);

        String attName = attackerName == null ? "Attacker" : attackerName.name;
        String defName = defenderName == null ? "Defender" : defenderName.name;

        Log.debug(Log.Logger.BATTLE,
                  attName + " attacked " + defName + " and inflicted " + damageTaken + " damage.");

        if (!defender.isAlive)
        {
            Name   nameContainer = defenderEntity.getComponent(Name.class);
            String name          = nameContainer == null ? "Enemy" : nameContainer.name;
            Log.debug(Log.Logger.BATTLE, name + " died");

            m_shouldUpdateBattlefieldPosition = true;
        }
    }

    private void selectEnemyAttackAgainstPlayer(List<Entity> entities, Entity attacker)
    {
        CombatInfo enemy = attacker.getComponent(CombatInfo.class);
        if (enemy == null)
        {
            return;
        }

        for (Entity entity : entities)
        {
            if (entity.hasComponent(PlayerTeam.class))
            {
                int defenderIndex = entity.getComponent(CombatInfo.class).battlefieldPosition;
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

    private class EntityBattlefieldPositionComparator implements Comparator<Entity>
    {
        @Override
        public int compare(Entity o1, Entity o2)
        {
            if (o1.equals(o2))
            {
                return 0;
            }
            RenderPosition pos1 = o1.getComponent(RenderPosition.class);
            RenderPosition pos2 = o2.getComponent(RenderPosition.class);

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
