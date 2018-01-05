package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.states.State;

import java.util.Stack;

public class StateStacker
{
    private final Stack<State> m_states = new Stack<State>();

    public StateStacker(State firstState)
    {
        m_states.add(firstState);
    }

    public void update(float delta)
    {
        if(m_states.size() != 0)
        {
            m_states.peek().update(delta);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer)
    {
        if(m_states.size() != 0)
        {
            m_states.peek().render(batch, shapeRenderer);
        }
    }

    public void push(State state)
    {
        m_states.push(state);
    }

    public void pop()
    {
        m_states.pop().dispose();
        if(m_states.empty())
        {
            Gdx.app.exit();
        }
    }
}
