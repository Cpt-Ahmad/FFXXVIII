package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;

import java.util.List;

public abstract class EntityRenderSystem extends EntitySystem
{
    public EntityRenderSystem(EntitySystemType entitySystemType)
    {
        super(entitySystemType);
    }

    public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer, List<Entity> entities);
}
