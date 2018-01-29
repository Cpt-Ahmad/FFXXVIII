package de.captain.ffxxviii.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.EntityHandler;
import de.captain.ffxxviii.entity.components.CombatInfo;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.presets.Goblin;
import de.captain.ffxxviii.entity.systems.BattleSystem;
import de.captain.ffxxviii.entity.systems.TextureRenderer;
import de.captain.ffxxviii.main.StateStacker;

import java.util.ArrayList;
import java.util.List;

/*
TODO delete BattleSystem class and put everything in here
 */
public class Battle extends State
{
    private EntityHandler m_entityHandler = new EntityHandler();
    private OrthographicCamera m_camera;

    public Battle(SpriteBatch batch, ShapeRenderer shapeRenderer, StateStacker stateStacker, List<Entity> playerTeam)
    {
        super(batch, shapeRenderer, stateStacker);
        if (playerTeam.size() > 3 || playerTeam.size() == 0)
        {
            throw new IllegalArgumentException(
                    "Player team can have up to 3 entities and has to be non-zero (" + playerTeam.size() + " in it)");
        }

        m_camera = new OrthographicCamera();
        m_camera.setToOrtho(false, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

        for (int i = 0; i < playerTeam.size(); i++)
        {
            Entity         entity    = playerTeam.get(i);
            RenderPosition renderPos = entity.getComponent(RenderPosition.class);
            CombatInfo     cInfo     = entity.getComponent(CombatInfo.class);

            renderPos.set(50 + (cInfo.isFrontLine ? 75 : 0), 100 + 75 * i);
        }

        m_entityHandler.addSystem(new BattleSystem());
        m_entityHandler.addSystem(new TextureRenderer());

        m_entityHandler.addEntities(playerTeam);
        m_entityHandler.addEntities(createEnemyTeam(6));
    }

    @Override
    public void update(float delta)
    {
        m_entityHandler.update();

        m_camera.update();
    }

    @Override
    public void render()
    {
        m_batch.setProjectionMatrix(m_camera.combined);
        m_shapeRenderer.setProjectionMatrix(m_camera.combined);

        m_entityHandler.render(m_batch, m_shapeRenderer);
    }

    @Override
    public void onEnter()
    {
        Gdx.input.setInputProcessor(new InputMultiplexer(m_guiHandler.getStage(), new BattleInputProcessor()));
    }

    @Override
    public void dispose()
    {
    }

    private List<Entity> createEnemyTeam(int sizeOfTeam)
    {
        if (sizeOfTeam < 1 || sizeOfTeam > 6)
        {
            throw new IllegalArgumentException("Enemy team size has to be between 1 and 6");
        }

        List<Entity> enemyTeam = new ArrayList<>();

        // TODO add implementation of enemy team generator
        for (int i = 0; i < sizeOfTeam; i++)
        {
            float   x, y;
            boolean isFrontLine;

            x = 400 + (i % 2) * 75;
            y = 100 + (i % 3) * 75;
            isFrontLine = i % 2 == 0;

            Goblin goblin = new Goblin(x, y, isFrontLine);
            enemyTeam.add(goblin);
        }

        return enemyTeam;
    }

    private class BattleInputProcessor extends InputAdapter
    {
    }
}
