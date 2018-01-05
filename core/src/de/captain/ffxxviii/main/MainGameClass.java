package de.captain.ffxxviii.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.states.MainMenu;

public class MainGameClass extends ApplicationAdapter
{
    private final SpriteBatch   m_batch         = new SpriteBatch();
    private final ShapeRenderer m_shapeRenderer = new ShapeRenderer();

    private StateStacker m_stateStacker;

    @Override
    public void create()
    {
        // Load assets

        // Set first state
        m_stateStacker = new StateStacker(new MainMenu(m_batch));
    }

    @Override
    public void render()
    {
        m_stateStacker.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stateStacker.render(m_batch, m_shapeRenderer);
    }

    @Override
    public void dispose()
    {
        m_batch.dispose();
        m_shapeRenderer.dispose();
    }
}
