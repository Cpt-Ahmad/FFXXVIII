package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.math.Rectangle;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.entity.util.Mapper;
import de.captain.ffxxviii.util.RectangleX;

public class MovementCollisionSystem extends EntitySystemListener
{
    public MovementCollisionSystem()
    {
        super(EntitySystemType.MOVEMENT_COLLISION, Family.all(Hitbox.class, Blocking.class, Velocity.class).get(), false);
    }

    @Override
    public void update(float deltaTime)
    {
        for(Entity moving : m_entities)
        {
            Hitbox hitbox = Mapper.HITBOX.get(moving);
            Velocity vel = Mapper.VELOCITY.get(moving);
            Rectangle testHitbox = new RectangleX(hitbox.hitbox).translate(vel.velocityVector);
            boolean isMovable = Mapper.BLOCKING.get(moving).isMovable;

            if(!isMovable) continue;

            for(Entity blocking : m_entities)
            {
                if(moving == blocking) continue;

                boolean isOtherMovable = Mapper.BLOCKING.get(blocking).isMovable;
                Hitbox otherHitbox = Mapper.HITBOX.get(moving);

                if(!isOtherMovable)
                {
                    // TODO
                }
            }
        }
    }
}
