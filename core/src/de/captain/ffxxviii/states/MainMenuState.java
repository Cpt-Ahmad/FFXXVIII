package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.captain.ffxxviii.main.StateStacker;

import javax.swing.event.ChangeEvent;

public class MainMenuState extends State
{

    public MainMenuState(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker)
    {
        super(batch, shapeRenderer, stateStacker);
    }

    @Override
    public void update(float delta)
    {
        m_guiHandler.update(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            if(m_stateStacker == null)
            {
                Gdx.app.debug("State", "Null");
            }
            m_stateStacker.push(new IngameState(m_batch, m_shapeRenderer, m_stateStacker));
        }
    }

    @Override
    public void render()
    {
        // Indication that you are in the main menu
        // Will be replaced by proper main menu
        m_shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        m_shapeRenderer.setColor(Color.RED);
        m_shapeRenderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        m_shapeRenderer.end();

        m_guiHandler.render();
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(m_guiHandler.getStage());
    }

    @Override
    public void dispose()
    {
        m_guiHandler.dispose();
    }
}
