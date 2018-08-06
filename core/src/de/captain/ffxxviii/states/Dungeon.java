package de.captain.ffxxviii.states;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import de.captain.ffxxviii.entity.components.Position;
import de.captain.ffxxviii.entity.components.Velocity;
import de.captain.ffxxviii.entity.presets.Goblin;
import de.captain.ffxxviii.entity.systems.MovementSystem;
import de.captain.ffxxviii.entity.systems.RenderSystem;
import de.captain.ffxxviii.main.StateStacker;
import de.captain.ffxxviii.main.WorldMap;

public class Dungeon extends State
{
    private final Entity m_player;

    private Engine   m_engine = new Engine();
    private WorldMap m_world  = new WorldMap();

    Dungeon(StateStacker stateStacker, Entity player)
    {
        super(stateStacker);
        m_player = player;
        m_engine.addEntity(player);

        m_camera.position.set(0f, 0f, 0f);
        m_camera.update();

        m_world.load("world01.tmx", m_engine);

        m_engine.addSystem(new MovementSystem());
        m_engine.addSystem(new RenderSystem(m_world.getMap(), m_camera));
    }

    @Override
    public void update(float delta)
    {
        // Player Movement
        Velocity vel = m_player.getComponent(Velocity.class);
        if (vel == null) throw new NullPointerException("the player has to have a velocity component");
        float dx = 0f, dy = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy = 1f;
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) dy = -1f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx = -1f;
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) dx = 1f;
        vel.setAndNorm(dx, dy);

        // Player Attacking
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
        }

        Position position = m_player.getComponent(Position.class);
        if(position == null) throw new NullPointerException("the player has to have a position component");
        m_camera.position.set(position.positionVector, 0f);

        m_engine.update(delta);
    }

    @Override
    public void onEnter()
    {
    }

    @Override
    public void dispose()
    {
        for (EntitySystem system : m_engine.getSystems())
        {
            m_engine.removeSystem(system);
        }
    }
}
