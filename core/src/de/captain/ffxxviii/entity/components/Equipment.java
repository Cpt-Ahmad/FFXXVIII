package de.captain.ffxxviii.entity.components;

import de.captain.ffxxviii.item.Item;

public class Equipment implements Component
{
    public Item weapon, armor;

    public Equipment()
    {
    }

    public Equipment(Item weapon, Item armor)
    {
        this.weapon = weapon;
        this.armor = armor;
    }

    public Item switchWeapon(Item item)
    {
        if (!item.type.equals("weapon"))
        {
            throw new IllegalArgumentException(String.format("item is not a weapon (%s)", item.name));
        }

        Item tmp = weapon;
        weapon = item;
        return tmp;
    }

    public Item switchArmor(Item item)
    {
        if (!item.type.equals("armor"))
        {
            throw new IllegalArgumentException(String.format("item is not a armor (%s)", item.name));
        }

        Item tmp = armor;
        armor = item;
        return tmp;
    }
}
