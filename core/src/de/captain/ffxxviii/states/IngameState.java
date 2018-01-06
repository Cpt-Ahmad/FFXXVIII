package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.main.StateStacker;

public class IngameState extends State
{
    private TiledMap                   m_world;
    private OrthogonalTiledMapRenderer m_worldRenderer;
    private OrthographicCamera         m_camera;

    private final EntityHandler m_entityHandler = new EntityHandler();

    public IngameState(SpriteBatch m_batch, ShapeRenderer m_shapeRenderer, StateStacker m_stateStacker)
    {
        super(m_batch, m_shapeRenderer, m_stateStacker);

        m_camera = new OrthographicCamera();
        m_camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        loadMap("world01.tmx");
    }

    @Override
    public void update(float delta)
    {
        m_guiHandler.update(delta);
        m_entityHandler.update();
        m_worldRenderer.setView(m_camera);
    }

    @Override
    public void render()
    {
        m_worldRenderer.render();
        m_entityHandler.render(m_batch, m_shapeRenderer);
        m_guiHandler.render();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new IngameInputProcessor()));
    }

    @Override
    public void dispose()
    {
        m_world.dispose();
        m_worldRenderer.dispose();
    }

    private void loadMap(String file)
    {
        m_world = new TmxMapLoader().load("maps/" + file);
        if (m_worldRenderer == null)
        {
            m_worldRenderer = new OrthogonalTiledMapRenderer(m_world, m_batch);
        } else
        {
            m_worldRenderer.setMap(m_world);
        }
    }

    private class IngameInputProcessor extends InputAdapter
    {
        @Override
        public boolean keyDown(int keycode)
        {
            if (keycode == Input.Keys.ESCAPE)
            {
                m_stateStacker.pop();
            }

            return false;
        }

        @Override
        public boolean keyUp(int keycode)
        {
            return false;
        }
    }
}
