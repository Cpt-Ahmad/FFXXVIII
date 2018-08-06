package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.captain.ffxxviii.entity.util.Mapper;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.Assets;

@Deprecated
public class TextureRenderer extends EntitySystem
{
    private ImmutableArray<Entity> m_entities;

    public TextureRenderer()
    {
        super(EntitySystemType.TEXTURE_RENDERER.priority);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        m_entities = engine.getEntitiesFor(Family.all(TextureContainer.class).one(RenderPosition.class,
                                                                                  de.captain.ffxxviii.entity.components.GridPosition.class)
                                                 .get());
    }

    @Override
    public void update(float deltaTime)
    {
        SpriteBatch batch = Assets.getSpriteBatch();
        batch.begin();

        for (Entity entity : m_entities)
        {
            TextureContainer textureContainer = Mapper.TEXTURE_CONTAINER.get(entity);
            RenderPosition   renderPosition   = Mapper.RENDER_POSITION.get(entity);
            GridPosition     gridPosition     = Mapper.GRID_POSITION.get(entity);

            if (renderPosition != null) batch.draw(textureContainer.texture, renderPosition.x, renderPosition.y);
            else batch.draw(textureContainer.texture, gridPosition.x, gridPosition.y);
        }

        batch.end();
    }
}
