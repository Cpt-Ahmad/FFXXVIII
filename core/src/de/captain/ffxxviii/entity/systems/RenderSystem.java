package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.captain.ffxxviii.entity.components.AnimationContainer;
import de.captain.ffxxviii.entity.components.Position;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.entity.util.EntityRenderComparator;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.WorldMap;

public class RenderSystem extends EntitySystemListener
{
    private final EntityRenderComparator m_renderComparator = new EntityRenderComparator();

    private final OrthographicCamera         m_camera;
    private       OrthogonalTiledMapRenderer m_mapRenderer;

    public RenderSystem(OrthographicCamera camera)
    {
        super(EntitySystemType.RENDER_SYSTEM,
              Family.all(Position.class).one(AnimationContainer.class, TextureContainer.class).get(), true);
        m_camera = camera;
    }

    public RenderSystem(TiledMap map, OrthographicCamera camera)
    {
        this(camera);
        m_mapRenderer = new OrthogonalTiledMapRenderer(map, Assets.getSpriteBatch());
        m_mapRenderer.setView(camera);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        m_entities.sort(m_renderComparator);
    }

    @Override
    public void update(float deltaTime)
    {
        // TODO make the sorting more efficient, e.g. ignore the not-moving entities
        m_entities.sort(m_renderComparator);

        m_camera.update();
        m_mapRenderer.setView(m_camera);

        SpriteBatch batch = Assets.getSpriteBatch();
        batch.setProjectionMatrix(m_camera.combined);
        batch.begin();

        TiledMapTileLayer tmLayer =
                (TiledMapTileLayer) m_mapRenderer.getMap().getLayers().get(WorldMap.RenderLayer.BACKGROUND.identifier);
        m_mapRenderer.renderTileLayer(tmLayer);

        for (Entity entity : m_entities)
        {
            TextureRegion texture;

            AnimationContainer animCon = entity.getComponent(AnimationContainer.class);
            if (animCon == null)
            {
                texture = entity.getComponent(TextureContainer.class).texture;
            } else
            {
                texture = animCon.animation.getKeyFrame(deltaTime);
            }

            Position pos = entity.getComponent(Position.class);

            batch.draw(texture, pos.positionVector.x, pos.positionVector.y);
        }

        batch.end();
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        m_mapRenderer.dispose();
    }
}
