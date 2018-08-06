package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.presets.Player;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.StateStacker;

public class MainMenu extends State
{

    public MainMenu(StateStacker stateStacker)
    {
        super(stateStacker);
    }

    @Override
    public void update(float delta)
    {
        m_guiHandler.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched())
        {
            m_stateStacker.push(new Dungeon(m_stateStacker, new Player()));
        }

        render();
    }

    private void render()
    {
        // Indication that you are in the main menu
        // Will be replaced by proper main menu
        ShapeRenderer shapeRenderer = Assets.getShapeRenderer();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

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
