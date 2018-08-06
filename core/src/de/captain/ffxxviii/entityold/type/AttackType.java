package de.captain.ffxxviii.entityold.type;

public enum AttackType
{
    BASIC_ATTACK(10, 1000, Element.NONE),
    HEAVY_ATTACK(30, 2500, Element.NONE),;

    public final int     damage;
    public final int     staminaRequired;
    public final Element element;

    AttackType(int damage, int staminaRequired, Element element)
    {
        this.damage = damage;
        this.staminaRequired = staminaRequired;
        this.element = element;
    }
}
