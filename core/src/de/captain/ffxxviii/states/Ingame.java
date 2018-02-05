package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.entity.presets.Player;
import de.captain.ffxxviii.entity.systems.CollisionSystem;
import de.captain.ffxxviii.entity.systems.MapGridRenderer;
import de.captain.ffxxviii.entity.systems.MovementSystem;
import de.captain.ffxxviii.entity.systems.TextureRenderer;
import de.captain.ffxxviii.main.StateStacker;
import de.captain.ffxxviii.main.WorldMap;

import java.util.List;

public class Ingame extends State
{
    private final WorldMap m_world;

    private Entity m_player;

    private final EntityHandler m_entityHandler = new EntityHandler();

    Ingame(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker)
    {
        super(batch, shapeRenderer, stateStacker);

        m_world = new WorldMap(batch);
        m_world.loadMap("maps/test-map.tmx", m_entityHandler.getEntityList());

        m_entityHandler.addSystem(new CollisionSystem());
        m_entityHandler.addSystem(new MovementSystem());
        m_entityHandler.addSystem(new TextureRenderer());
        m_entityHandler.addSystem(new MapGridRenderer(m_world));

        m_player = new Player();
        m_entityHandler.addEntity(m_player);
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
        m_world.update(m_camera);
    }

    @Override
    public void render()
    {
        m_batch.setProjectionMatrix(m_camera.combined);
        m_shapeRenderer.setProjectionMatrix(m_camera.combined);

        m_world.render();
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
            renderPos.set(gridPos.x * WorldMap.getTileSize(), gridPos.y * WorldMap.getTileSize());
        }
    }

    @Override
    public void dispose()
    {
        m_world.dispose();
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
                return true;
            } else if (keycode == Input.Keys.I)
            {
                m_stateStacker.push(new InventoryMenu(m_batch, m_shapeRenderer, m_stateStacker, m_player.getComponent(Inventory.class)));
                return true;
            } else
            {
                return false;
            }
        }

        @Override
        public boolean keyUp(int keycode)
        {
            return false;
        }
    }
}
