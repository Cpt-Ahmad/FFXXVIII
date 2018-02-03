package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import de.captain.ffxxviii.states.State;

import java.util.Stack;

public class StateStacker
{
    private final Stack<State> m_states = new Stack<State>();

    public void update(float delta)
    {
        if(m_states.size() != 0)
        {
            m_states.peek().update(delta);
        }
    }

    public void render()
    {
        if(m_states.size() != 0)
        {
            m_states.peek().render();
        }
    }

    public void push(State state)
    {
        state.onEnter();
        m_states.push(state);
    }

    public void pop()
    {
        if(m_states.empty())
        {
            Gdx.app.exit();
            return;
        }

        m_states.pop().dispose();
        if(m_states.empty())
        {
            Gdx.app.exit();
        }
        m_states.peek().onEnter();
    }
}
