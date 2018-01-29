package de.captain.ffxxviii.entity.systems;

public abstract class EntitySystem
{
    public enum EntitySystemType
    {
        MOVEMENT(2, true),
        COLLISION(1, true),
        BATTLE(1, true),

        /* */

        TEXTURE_RENDERER(1, false),
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
