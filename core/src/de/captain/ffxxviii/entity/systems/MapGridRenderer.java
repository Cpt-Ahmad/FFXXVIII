package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.main.WorldMap;

public class MapGridRenderer extends EntitySystem
{
    private WorldMap m_worldMap;

    public MapGridRenderer(WorldMap worldMap)
    {
        super(EntitySystemType.MAP_GRID_RENDERER.priority);
        m_worldMap = worldMap;
    }

    @Override
    public void update(float deltaTime)
    {
        ShapeRenderer shapeRenderer = Assets.getShapeRenderer();
        int           width         = m_worldMap.getMap().getProperties().get("width", Integer.class);
        int           height        = m_worldMap.getMap().getProperties().get("height", Integer.class);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int i = 0; i < height; i++)
        {
            shapeRenderer
                    .line(0, i * WorldMap.getTileSize(), width * WorldMap.getTileSize(), i * WorldMap.getTileSize());
        }
        for (int j = 0; j < width; j++)
        {
            shapeRenderer
                    .line(j * WorldMap.getTileSize(), 0, j * WorldMap.getTileSize(), height * WorldMap.getTileSize());
        }
        shapeRenderer.end();
    }
}

