package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.entity.presets.BattleArrow;
import de.captain.ffxxviii.entity.type.AttackType;
import de.captain.ffxxviii.util.Log;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BattleSystem extends EntityUpdateSystem
{
    private boolean m_shouldUpdateBattlefieldPosition = true;
    private boolean m_isArrowAdded                    = false;
    private boolean m_isBattleInWaitingMode           = false;

    private int m_cursor;
    private int m_battlingEntityCount;

    private final Entity m_arrow;
    private       Entity m_currentEntity;

    public BattleSystem()
    {
        super(EntitySystemType.BATTLE);
        m_arrow = new BattleArrow();
    }

    @Override
    public void update(List<Entity> entities)
    {
        // Add the selection arrow to the entity list, in case its not already added
        if (!m_isArrowAdded)
        {
            entities.add(m_arrow);
            m_isArrowAdded = true;
        }

        // Update the battlefield position of the entities, if necessary
        if (m_shouldUpdateBattlefieldPosition)
        {
            setBattlefieldPosition(entities);
            if (m_cursor >= m_battlingEntityCount)
            {
                m_cursor = m_battlingEntityCount - 1;
            }
        }

        // move the selection cursor around
        if (Gdx.input.isKeyJustPressed(Input.Keys.W))
        {
            m_cursor--;
            if (m_cursor < 0)
            {
                m_cursor = m_battlingEntityCount - 1;
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S))
        {
            m_cursor++;
            if (m_cursor == m_battlingEntityCount)
            {
                m_cursor = 0;
            }
        }

        // player has to select a target and hit space to confirm
        if (m_currentEntity != null)
        {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            {
                Log.debug(Log.Logger.BATTLE, "Player chose attack");
                CombatInfo cInfoAttacker = m_currentEntity.getComponent(CombatInfo.class);
                cInfoAttacker.setAttack(AttackType.BASIC_ATTACK, m_cursor);
                m_currentEntity = null;
                m_isBattleInWaitingMode = false;
            }
        }

        for (Entity entity : entities)
        {
            CombatInfo attacker = entity.getComponent(CombatInfo.class);

            if (attacker == null)
            {
                continue;
            }

            // Sets the render position of the selection arrow
            if (m_cursor ==
                attacker.battlefieldPosition) // entity is selected by arrow (sets the position of the arrow)
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
                    attack(entity, getEntityByBattlefieldPosition(attacker.getTarget(), entities));
                } else if (!attacker.isAttackSelected()) // is an attack selected for this entity
                {
                    // Is the entity an enemy -> select an attack and target
                    if (entity.hasComponent(Enemy.class))
                    {
                        attackPlayer(entities, entity);
                        Log.debug(Log.Logger.BATTLE, "Enemy chose attack");
                        m_isBattleInWaitingMode = false;
                    } else if (entity.hasComponent(PlayerTeam.class)) // if its a player, set the battle to waiting mode
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

        // Remove every non-player entity that is dead
        for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext(); )
        {
            Entity     entity = iterator.next();
            CombatInfo cInfo  = entity.getComponent(CombatInfo.class);
            if (cInfo == null || !entity.hasComponent(Enemy.class))
            {
                continue;
            }
            if (!cInfo.isAlive)
            {
                iterator.remove();
            }
        }
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

        Log.debug(Log.Logger.BATTLE, attName + " attacked " + defName + " and inflicted " + damageTaken + " damage.");

        if (!defender.isAlive)
        {
            Name   nameContainer = defenderEntity.getComponent(Name.class);
            String name          = nameContainer == null ? "Enemy" : nameContainer.name;
            Log.debug(Log.Logger.BATTLE, name + " died");

            m_shouldUpdateBattlefieldPosition = true;
        }
    }

    private void attackPlayer(List<Entity> entities, Entity attacker)
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

    private Entity getEntityByBattlefieldPosition(int index, List<Entity> entities)
    {
        CombatInfo cInfo;
        for (Entity entity : entities)
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
}
