package de.captain.ffxxviii.entity.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class EntityRenderSystem extends EntitySystem
{
    public EntityRenderSystem(EntitySystemType entitySystemType)
    {
        super(entitySystemType);
    }

    public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer);
}
