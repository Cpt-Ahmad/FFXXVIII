package de.captain.ffxxviii.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import de.captain.ffxxviii.entity.components.Dimension;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.GridVelocity;
import de.captain.ffxxviii.entity.presets.Player;
import de.captain.ffxxviii.entity.systems.*;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.StateStacker;
import de.captain.ffxxviii.main.WorldMap;

@Deprecated
public class Ingame extends State
{
    private final WorldMap m_world;

    private Entity m_player;

    private final Engine m_engine = new Engine();

    Ingame(StateStacker stateStacker)
    {
        super(stateStacker);

        m_world = new WorldMap();
        //m_world.loadMap("maps/test-map.tmx", m_engine.getEntityList());

        m_engine.addSystem(new MovementCollisionSystem());
        m_engine.addSystem(new MovementSystem());
        m_engine.addSystem(new TextureRenderer());
        m_engine.addSystem(new MapGridRenderer(m_world));

        m_player = new Player();
        m_engine.addEntity(m_player);
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
        m_engine.update(delta);

        de.captain.ffxxviii.entity.components.RenderPosition
                renderPos = m_player.getComponent(de.captain.ffxxviii.entity.components.RenderPosition.class);
        Dimension dim = m_player.getComponent(Dimension.class);
        if (renderPos != null && dim != null)
        {
            m_camera.position.set(renderPos.x + dim.width / 2f, renderPos.y + dim.height / 2f, 0f);
        }

        m_camera.update();
    }

    public void render()
    {
        Assets.getSpriteBatch().setProjectionMatrix(m_camera.combined);
        Assets.getShapeRenderer().setProjectionMatrix(m_camera.combined);

        //m_world.render();
        //m_engine.render(m_batch, m_shapeRenderer);
        m_guiHandler.render();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new IngameInputProcessor()));
        /*
        for (Entity entity : m_engine.getEntityList())
        {
            GridPosition gridPos = entity.getComponent(GridPosition.class);
            de.captain.ffxxviii.entity.components.RenderPosition renderPos = entity.getComponent(
                    de.captain.ffxxviii.entity.components.RenderPosition.class);

            if (gridPos == null || renderPos == null)
            {
                continue;
            }
            renderPos.set(gridPos.x * WorldMap.getTileSize(), gridPos.y * WorldMap.getTileSize());
        }
        */
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
            switch (keycode)
            {
                case Input.Keys.ESCAPE:
                    m_stateStacker.pop();
                    return true;

                case Input.Keys.B:
                    m_stateStacker.push(new Battle(m_stateStacker, m_player));
                    return true;

                case Input.Keys.I:
                    m_stateStacker.push(new InventoryMenu(m_stateStacker,
                                                          m_player.getComponent(
                                                                  de.captain.ffxxviii.entity.components.Inventory.class)));
                    return true;

                case Input.Keys.SPACE:
                    GridPosition playerPos = m_player.getComponent(GridPosition.class);

                    GridPosition entityPos;
                    de.captain.ffxxviii.entity.components.TileInfo info;
/*
                    for (Entity entity : m_engine.getEntityList())
                    {
                        entityPos = entity.getComponent(GridPosition.class);
                        info = entity.getComponent(de.captain.ffxxviii.entity.components.TileInfo.class);
                        if (entityPos != null && info != null && entityPos.equals(playerPos) && info.type ==
                                                                                                de.captain.ffxxviii.entity.components.TileInfo.TileInfoType.INTERACTION)
                        {
                            Log.debug(Log.Logger.INGAME, "Interaction at " + entityPos.toString());
                        }
                    }
                    */
                    return true;

                default:
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
