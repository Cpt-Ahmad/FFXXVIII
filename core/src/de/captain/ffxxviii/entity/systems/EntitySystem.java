package de.captain.ffxxviii.entity.systems;

public abstract class EntitySystem
{
    public enum EntitySystemType
    {
        MOVEMENT(1, true),

        /* */

        ;

        public final int     priority;
        public final boolean isUpdateSystem;

        EntitySystemType(int priority, boolean isUpdateSystem)
        {
            this.priority = priority;
            this.isUpdateSystem = isUpdateSystem;
        }
    }

    public final EntitySystemType entitySystemType;

    public EntitySystem(EntitySystemType entitySystemType)
    {
        this.entitySystemType = entitySystemType;
    }
}
