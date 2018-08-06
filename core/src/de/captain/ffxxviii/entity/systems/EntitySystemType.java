package de.captain.ffxxviii.entity.systems;

public enum EntitySystemType
{
    MOVEMENT_COLLISION(1, true),
    MOVEMENT(2, true),

    /* */

    TEXTURE_RENDERER(3, false),
    MAP_GRID_RENDERER(2, false),
    WORLD_MAP_RENDERER(1, false),
    RENDER_SYSTEM(0, false)
    ;

    public final int priority;

    private static final int RENDER_PRIORITY_OFFSET = 20;

    EntitySystemType(int priority, boolean isUpdateSystem)
    {
        this.priority = isUpdateSystem ? priority : priority + RENDER_PRIORITY_OFFSET;
    }
}
