package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.entity.presets.Player;
import de.captain.ffxxviii.entity.systems.CollisionSystem;
import de.captain.ffxxviii.entity.systems.MovementSystem;
import de.captain.ffxxviii.entity.systems.TextureRenderer;
import de.captain.ffxxviii.main.StateStacker;

import java.util.List;

public class Ingame extends State
{
    public static final int TILE_SIZE = 32;

    private TiledMap                   m_world;
    private OrthogonalTiledMapRenderer m_worldRenderer;

    private Entity m_player;

    private final EntityHandler m_entityHandler = new EntityHandler();

    public Ingame(SpriteBatch m_batch, ShapeRenderer m_shapeRenderer, StateStacker m_stateStacker)
    {
        super(m_batch, m_shapeRenderer, m_stateStacker);

        m_entityHandler.addSystem(new CollisionSystem());
        m_entityHandler.addSystem(new MovementSystem());
        m_entityHandler.addSystem(new TextureRenderer());

        m_player = new Player();
        m_entityHandler.addEntity(m_player);
        //m_entityHandler.addEntity(new PlayerTeamMember(true));
        //m_entityHandler.addEntity(new PlayerTeamMember(false));

        loadMap("world01.tmx");
    }

    @Override
    public void update(float delta)
    {
        // DEBUG
        GridVelocity gridVel = m_player.getComponent(GridVelocity.class);

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

        RenderPosition renderPos = m_player.getComponent(RenderPosition.class);
        Dimension      dim       = m_player.getComponent(Dimension.class);
        if (renderPos != null && dim != null)
        {
            m_camera.position.set(renderPos.x + dim.width / 2f, renderPos.y + dim.height / 2f, 0f);
        }

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
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new IngameInputProcessor()));
        for (Entity entity : m_entityHandler.getEntityList())
        {
            GridPosition   gridPos   = entity.getComponent(GridPosition.class);
            RenderPosition renderPos = entity.getComponent(RenderPosition.class);

            if (gridPos == null || renderPos == null)
            {
                continue;
            }
            renderPos.set(gridPos.x * TILE_SIZE, gridPos.y * TILE_SIZE);
        }
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
            } else if (keycode == Input.Keys.B)
            {
                List<Entity> playerTeam = m_entityHandler.getEntities(PlayerTeam.class);
                m_stateStacker.push(new Battle(m_batch, m_shapeRenderer, m_stateStacker, playerTeam, m_player));
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
