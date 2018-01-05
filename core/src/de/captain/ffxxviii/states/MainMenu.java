package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import javax.swing.event.ChangeEvent;

public class MainMenu extends State
{
    public MainMenu(SpriteBatch batch)
    {
        super(batch);
    }

    @Override
    public void update(float delta)
    {
        m_guiHandler.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer)
    {
        m_guiHandler.render();
    }

    @Override
    public void dispose()
    {
        m_guiHandler.dispose();
    }
}
