package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.captain.ffxxviii.gui.GuiHandler;

public abstract class State implements Disposable
{
    protected final GuiHandler m_guiHandler;

    public State(SpriteBatch batch)
    {
        m_guiHandler = new GuiHandler(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), batch);
    }

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch, ShapeRenderer shapeRenderer);
}
