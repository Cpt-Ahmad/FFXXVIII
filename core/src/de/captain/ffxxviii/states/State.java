package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.captain.ffxxviii.gui.GuiHandler;
import de.captain.ffxxviii.main.StateStacker;

public abstract class State implements Disposable
{
    protected final SpriteBatch        m_batch;
    protected final ShapeRenderer      m_shapeRenderer;
    protected final OrthographicCamera m_camera;

    protected final GuiHandler   m_guiHandler;
    protected final StateStacker m_stateStacker;

    public State(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker)
    {
        m_batch = batch;
        m_shapeRenderer = shapeRenderer;
        m_stateStacker = stateStacker;
        m_guiHandler = new GuiHandler(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), batch);

        m_camera = new OrthographicCamera();
        m_camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
    }

    public abstract void update(float delta);

    public abstract void render();

    public abstract void onEnter();
}
