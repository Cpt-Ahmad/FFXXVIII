package de.captain.ffxxviii.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import de.captain.ffxxviii.item.Items;
import de.captain.ffxxviii.states.MainMenu;
import de.captain.ffxxviii.util.Log;
import de.captain.ffxxviii.util.Testing;

import java.util.ArrayList;
import java.util.List;


/*
TODO add enemies
TODO add attacks
TODO add enemy movement
 */
public class MainGameClass extends ApplicationAdapter
{
    private int m_ticksForFpsPrinting = 0;

    private StateStacker m_stateStacker;

    @Override
    public void create()
    {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        Testing.get().test();
        List<Log.Logger> logList = new ArrayList<>();
        logList.add(Log.Logger.BATTLE);
        logList.add(Log.Logger.ITEM);
        logList.add(Log.Logger.MAP);
        logList.add(Log.Logger.INGAME);
        Log.setLogList(logList);

        m_stateStacker = new StateStacker();

        Assets.init();
        Items.init();

        m_stateStacker.push(new MainMenu(m_stateStacker));
    }

    @Override
    public void render()
    {
        if (Debug.FPS_OUTPUT_IN_CONSOLE)
        {
            m_ticksForFpsPrinting++;
            if (m_ticksForFpsPrinting == 60)
            {
                m_ticksForFpsPrinting = 0;
                Gdx.app.debug("Fps", Gdx.graphics.getFramesPerSecond() + "");
            }
        }

        Testing.get().test();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the states
        m_stateStacker.update(Gdx.graphics.getDeltaTime());

        // Render the states
        //m_stateStacker.render();
    }

    @Override
    public void dispose()
    {
        Assets.dispose();
    }
}
