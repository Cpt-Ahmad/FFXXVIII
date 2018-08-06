package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;
import de.captain.ffxxviii.gui.GuiHandler;
import de.captain.ffxxviii.main.StateStacker;

public abstract class State implements Disposable
{
    protected final OrthographicCamera m_camera;

    protected final GuiHandler   m_guiHandler;
    protected final StateStacker m_stateStacker;

    public State(StateStacker stateStacker)
    {
        m_stateStacker = stateStacker;
        m_guiHandler = new GuiHandler(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        m_camera = new OrthographicCamera();
        m_camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    public abstract void update(float delta);

    public abstract void onEnter();
}
