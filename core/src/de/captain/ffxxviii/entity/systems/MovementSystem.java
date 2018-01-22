package de.captain.ffxxviii.entity.systems;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.GridVelocity;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.states.Ingame;

import java.util.List;

public class MovementSystem extends EntityUpdateSystem
{
    public MovementSystem()
    {
        super(EntitySystemType.MOVEMENT);
    }

    @Override
    public void update(List<Entity> entities)
    {
        GridPosition gridPos;
        RenderPosition renderPos;
        GridVelocity gridVel;

        for(Entity entity : entities)
        {
            gridPos = entity.getComponent(GridPosition.class);
            gridVel = entity.getComponent(GridVelocity.class);
            renderPos = entity.getComponent(RenderPosition.class);

            if(gridPos == null || gridVel == null || renderPos == null)
            {
                continue;
            }

            GridVelocity.Direction direction = gridVel.move();
            if(direction == GridVelocity.Direction.MOVING)
            {
                renderPos.translate(gridVel.getVelocity());
            } else if(direction != GridVelocity.Direction.NONE)
            {
                switch(direction)
                {
                    case UP:
                        gridPos.y++;
                        break;
                    case DOWN:
                        gridPos.y--;
                        break;
                    case LEFT:
                        gridPos.x--;
                        break;
                    case RIGHT:
                        gridPos.x++;
                        break;
                }
                renderPos.set(gridPos.x * Ingame.TILE_SIZE, gridPos.y * Ingame.TILE_SIZE);
            }
        }
    }
}
