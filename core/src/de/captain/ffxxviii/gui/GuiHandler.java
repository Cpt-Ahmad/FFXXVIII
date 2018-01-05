package de.captain.ffxxviii.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import de.captain.ffxxviii.main.Debug;

public class GuiHandler implements Disposable
{
    private Stage m_stage;
    private Table m_table;

    public GuiHandler(float viewportWidth, float viewportHeight, SpriteBatch spriteBatch)
    {
        m_stage = new Stage(new StretchViewport(viewportWidth, viewportHeight), spriteBatch);
        m_table = new Table();
        m_table.setFillParent(true);
        m_stage.addActor(m_table);

        if(Debug.SHOW_GUI_HITBOX)
        {
            m_table.debug();
        }
    }

    public void update(float delta)
    {
        m_stage.act(delta);
    }

    public void render()
    {
        m_stage.draw();
    }

    public <T extends Actor> Cell<T> add(T actor)
    {
        return m_table.add(actor);
    }

    public <T extends Actor> Cell<T> add(T actor, EventListener listener)
    {
        actor.addListener(listener);
        return m_table.add(actor);
    }

    @Override
    public void dispose()
    {
        m_stage.dispose();
    }
}
