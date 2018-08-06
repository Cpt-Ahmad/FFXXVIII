package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import de.captain.ffxxviii.entity.components.Position;
import de.captain.ffxxviii.entity.components.Velocity;
import de.captain.ffxxviii.entity.util.Mapper;

public class MovementSystem extends EntitySystemListener
{
    public MovementSystem()
    {
        super(EntitySystemType.MOVEMENT, Family.all(Position.class, Velocity.class).get(), false);
    }

    @Override
    public void update(float deltaTime)
    {
        for (Entity entity : m_entities)
        {
            Position pos = Mapper.POSITION.get(entity);
            Velocity vel = Mapper.VELOCITY.get(entity);

            pos.move(vel.velocityVector, deltaTime);
        }
    }
}
