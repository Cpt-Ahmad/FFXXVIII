package de.captain.ffxxviii.entity.util;

import com.badlogic.ashley.core.ComponentMapper;
import de.captain.ffxxviii.entity.components.*;

public class Mapper
{
    public static final ComponentMapper<GridPosition>     GRID_POSITION     =
            ComponentMapper.getFor(GridPosition.class);
    public static final ComponentMapper<GridVelocity>     GRID_VELOCITY     =
            ComponentMapper.getFor(GridVelocity.class);
    public static final ComponentMapper<RenderPosition>   RENDER_POSITION   =
            ComponentMapper.getFor(RenderPosition.class);
    public static final ComponentMapper<TextureContainer> TEXTURE_CONTAINER =
            ComponentMapper.getFor(TextureContainer.class);
    public static final ComponentMapper<Position>         POSITION          = ComponentMapper.getFor(Position.class);
    public static final ComponentMapper<Velocity>         VELOCITY          = ComponentMapper.getFor(Velocity.class);
    public static final ComponentMapper<Hitbox>           HITBOX            = ComponentMapper.getFor(Hitbox.class);
    public static final ComponentMapper<Blocking>         BLOCKING          = ComponentMapper.getFor(Blocking.class);

    private Mapper()
    {
    }
}
