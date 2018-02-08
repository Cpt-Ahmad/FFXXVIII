package de.captain.ffxxviii.item.components;

public class Weapon implements ItemComponent
{
    public enum WeaponType
    {
        MELEE,
        RANGED,
        MAGE
    }

    public final int attackPower;
    public final WeaponType type;

    public Weapon(int attackPower, WeaponType type)
    {
        this.attackPower = attackPower;
        this.type = type;
    }
}
