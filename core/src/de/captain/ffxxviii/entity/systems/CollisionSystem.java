package de.captain.ffxxviii.entity.systems;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.GridVelocity;
import de.captain.ffxxviii.entity.components.TileInfo;
import de.captain.ffxxviii.main.WorldMap;

import java.util.List;

public class CollisionSystem extends EntityUpdateSystem
{
    public CollisionSystem()
    {
        super(EntitySystemType.COLLISION);
    }

    @Override
    public void update(List<Entity> entities)
    {
        GridPosition posMoving;
        GridPosition posAfterMoving = new GridPosition();
        GridVelocity velMoving;

        GridPosition posBlocker;
        TileInfo     tile;

        for (Entity movingEntity : entities)
        {
            posMoving = movingEntity.getComponent(GridPosition.class);
            velMoving = movingEntity.getComponent(GridVelocity.class);

            if (posMoving == null || velMoving == null)
            {
                continue;
            }

            switch (velMoving.getDirection())
            {
                case UP:
                    posAfterMoving.set(posMoving.x, posMoving.y + 1);
                    break;
                case DOWN:
                    posAfterMoving.set(posMoving.x, posMoving.y - 1);
                    break;
                case LEFT:
                    posAfterMoving.set(posMoving.x - 1, posMoving.y);
                    break;
                case RIGHT:
                    posAfterMoving.set(posMoving.x + 1, posMoving.y);
                    break;
                default:
                    continue;
            }

            if (posAfterMoving.x >= 0 && posAfterMoving.x < WorldMap.getMapWidth() && posAfterMoving.y >= 0 &&
                posAfterMoving.y < WorldMap.getMapHeight())
            {
                for (Entity blockingEntity : entities)
                {
                    posBlocker = blockingEntity.getComponent(GridPosition.class);
                    tile = blockingEntity.getComponent(TileInfo.class);

                    if (posBlocker == null || tile == null)
                    {
                        continue;
                    }

                    if (tile.type == TileInfo.TileInfoType.BLOCKED && posAfterMoving.equals(posBlocker))
                    {
                        velMoving.cancelMovement();
                    }
                }
            } else
            {
                velMoving.cancelMovement();
            }
        }
    }
}
