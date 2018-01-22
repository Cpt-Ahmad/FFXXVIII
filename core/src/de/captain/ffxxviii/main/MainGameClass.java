package de.captain.ffxxviii.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.items.Item;
import de.captain.ffxxviii.states.MainMenu;
import de.captain.ffxxviii.util.Testing;

public class MainGameClass extends ApplicationAdapter
{
    private int m_ticksForFpsPrinting = 0;

    private SpriteBatch   m_batch;
    private ShapeRenderer m_shapeRenderer;

    private StateStacker m_stateStacker;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Testing.get().test();

        m_batch = new SpriteBatch();
        m_shapeRenderer = new ShapeRenderer();
        m_stateStacker = new StateStacker();

        Assets.getAssets().init();
        Item.init();

        m_stateStacker.push(new MainMenu(m_batch, m_shapeRenderer, m_stateStacker));
    }

    @Override
    public void render()
    {
        m_ticksForFpsPrinting++;
        if(m_ticksForFpsPrinting == 60)
        {
            m_ticksForFpsPrinting = 0;
            Gdx.app.debug("Fps", Gdx.graphics.getFramesPerSecond() + "");
        }

        m_stateStacker.update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_stateStacker.render();
    }

    @Override
    public void dispose()
    {
        m_batch.dispose();
        m_shapeRenderer.dispose();
    }
}
