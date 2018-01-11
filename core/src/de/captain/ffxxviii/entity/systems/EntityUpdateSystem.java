package de.captain.ffxxviii.entity.systems;

import de.captain.ffxxviii.entity.Entity;

import java.util.List;

public abstract class EntityUpdateSystem extends EntitySystem
{
    public EntityUpdateSystem(EntitySystemType entitySystemType)
    {
        super(entitySystemType);
    }

    public abstract void update(List<Entity> entities);
}
