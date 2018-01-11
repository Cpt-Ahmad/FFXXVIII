package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.GridVelocity;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.states.IngameState;

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

            if(gridVel.isReadyToStartMovement())
            {
                // TODO add collision checks here

                switch(gridVel.getDirection())
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
            }

            if(gridVel.move())
            {
                renderPos.set(gridPos.x * IngameState.TILE_SIZE, gridPos.y * IngameState.TILE_SIZE);
            } else
            {
                renderPos.translate(gridVel.getVelocity());
            }
        }
    }
}
