package de.captain.ffxxviii.entity.components;

import de.captain.ffxxviii.entity.type.AttackType;
import de.captain.ffxxviii.entity.type.Element;

public class CombatInfo implements Component
{
    public boolean isFrontLine;
    public boolean isAlive = true;

    public int maxHealth, health;
    public int staminaPerSec = 5, stamina = 0;
    public int battlefieldPosition = -1;

    private Attack nextAttack = new Attack();

    public CombatInfo(boolean isFrontLine, int maxHealth)
    {
        this.isFrontLine = isFrontLine;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void setAttack(AttackType attack, int target)
    {
        if (nextAttack.m_isFinished)
        {
            nextAttack.m_attack = attack;
            nextAttack.m_target = target;
            nextAttack.m_isFinished = false;
        }
    }

    public boolean isReadyToAttack()
    {
        return (!nextAttack.m_isFinished && stamina > nextAttack.m_attack.staminaRequired);
    }

    public void setAttackFinished()
    {
        nextAttack.m_isFinished = true;
        stamina = 0;
    }

    public boolean isAttackSelected()
    {
        return (!nextAttack.m_isFinished);
    }

    public int getAttackDamage()
    {
        return nextAttack.m_attack.damage;
    }

    public Element getAttackElement()
    {
        return nextAttack.m_attack.element;
    }

    public int damage(int damage, Element element)
    {
        health -= damage;
        isAlive = (health > 0);
        if(health < 0)
        {
            health = 0;
        } else if(health > maxHealth)
        {
            health = maxHealth;
        }

        return damage;
    }

    public int getTarget()
    {
        return nextAttack.m_target;
    }

    private class Attack
    {
        boolean m_isFinished = true;
        int        m_target;
        AttackType m_attack;

        private Attack()
        {
        }
    }
}
