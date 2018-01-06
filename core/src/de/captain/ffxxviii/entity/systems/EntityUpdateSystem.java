package de.captain.ffxxviii.entity.systems;

public abstract class EntityUpdateSystem extends EntitySystem
{
    public EntityUpdateSystem(EntitySystemType entitySystemType)
    {
        super(entitySystemType);
    }

    public abstract void update();
}
