package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.GridVelocity;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.systems.MovementSystem;
import de.captain.ffxxviii.main.StateStacker;

public class IngameState extends State
{
    public static final int TILE_SIZE = 32;

    private TiledMap                   m_world;
    private OrthogonalTiledMapRenderer m_worldRenderer;
    private OrthographicCamera         m_camera;

    private Entity player;

    private final EntityHandler m_entityHandler = new EntityHandler();

    public IngameState(SpriteBatch m_batch, ShapeRenderer m_shapeRenderer, StateStacker m_stateStacker)
    {
        super(m_batch, m_shapeRenderer, m_stateStacker);

        m_camera = new OrthographicCamera();
        m_camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        m_entityHandler.addSystem(new MovementSystem());

        // DEBUG
        player = new Entity();
        player.addComponents(new GridPosition(), new GridVelocity(30), new RenderPosition());

        m_entityHandler.addEntity(player);

        loadMap("world01.tmx");
    }

    @Override
    public void update(float delta)
    {
        // DEBUG
        GridVelocity gridVel = player.getComponent(GridVelocity.class);

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            gridVel.setDirection(GridVelocity.Direction.UP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            gridVel.setDirection(GridVelocity.Direction.DOWN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            gridVel.setDirection(GridVelocity.Direction.LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            gridVel.setDirection(GridVelocity.Direction.RIGHT);
        }

        m_guiHandler.update(delta);
        m_entityHandler.update();

        m_camera.update();
        m_worldRenderer.setView(m_camera);
    }

    @Override
    public void render()
    {
        m_batch.setProjectionMatrix(m_camera.combined);
        m_shapeRenderer.setProjectionMatrix(m_camera.combined);

        m_worldRenderer.render();
        m_entityHandler.render(m_batch, m_shapeRenderer);
        m_guiHandler.render();

        RenderPosition renderPos = player.getComponent(RenderPosition.class);
        GridPosition   gridPos   = player.getComponent(GridPosition.class);

        m_shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        m_shapeRenderer.setColor(Color.BLUE);
        m_shapeRenderer.rect(gridPos.x * TILE_SIZE, gridPos.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        m_shapeRenderer.setColor(Color.RED);
        m_shapeRenderer.rect(renderPos.x, renderPos.y, TILE_SIZE, TILE_SIZE);
        m_shapeRenderer.end();
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
                return true;
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
