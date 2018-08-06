package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import de.captain.ffxxviii.states.State;

import java.util.Stack;

public class StateStacker
{
    private final Stack<State> m_states = new Stack<>();

    public void update(float delta)
    {
        if (!m_states.empty()) m_states.peek().update(delta);
    }

    public void push(State state)
    {
        state.onEnter();
        m_states.push(state);
    }

    public void pop()
    {
        if (m_states.empty())
        {
            Gdx.app.exit();
            return;
        }

        m_states.pop().dispose();
        if (m_states.empty())
        {
            Gdx.app.exit();
        }
        m_states.peek().onEnter();
    }
}
