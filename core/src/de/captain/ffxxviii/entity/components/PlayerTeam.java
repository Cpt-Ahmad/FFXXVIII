package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

@Deprecated
public class PlayerTeam implements Component
{
    public final Entity melee, ranged, mage;
    public final Entity[] all;

    public PlayerTeam(Entity melee, Entity ranged, Entity mage)
    {
        this.melee = melee;
        this.ranged = ranged;
        this.mage = mage;

        all = new Entity[]{melee, ranged, mage};
    }

    public int size()
    {
        int size = 0;
        for (Entity entity : all)
        {
            if (entity != null) size++;
        }
        return size;
    }

    public boolean isEmpty()
    {
        return (melee == null && ranged == null && mage == null);
    }
}
