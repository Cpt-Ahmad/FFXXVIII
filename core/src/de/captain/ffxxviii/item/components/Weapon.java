package de.captain.ffxxviii.item.components;

import de.captain.ffxxviii.entity.type.Element;

public class Weapon implements ItemComponent
{
    public enum WeaponType
    {
        MELEE,
        RANGED,
        MAGE,
        UTILITY
    }

    public final int        attackPower;
    public final WeaponType type;
    public final Element    element;

    public Weapon(int attackPower, WeaponType type, Element element)
    {
        if (type == null) throw new IllegalArgumentException("the weapon type of a weapon item cannot be null");
        if (element == null) throw new IllegalArgumentException("the element of a weapon item cannot be null");

        this.attackPower = attackPower;
        this.type = type;
        this.element = element;
    }
}
