package de.captain.ffxxviii.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.TileInfo;
import de.captain.ffxxviii.entity.presets.InfoTile;
import de.captain.ffxxviii.util.Log;

import java.util.List;

public class WorldMap implements Disposable
{
    private static int s_tileSize, s_mapWidth, s_mapHeight;

    private TiledMap                   m_map;
    private OrthogonalTiledMapRenderer m_mapRenderer;

    public WorldMap(SpriteBatch batch)
    {
        m_mapRenderer = new OrthogonalTiledMapRenderer(null, batch);
    }

    public TiledMap getMap()
    {
        return m_map;
    }

    public void loadMap(String filename, List<Entity> entities)
    {
        if (m_map != null)
        {
            m_map.dispose();
        }
        m_map = new TmxMapLoader().load(filename);
        m_mapRenderer.setMap(m_map);

        MapProperties properties = m_map.getProperties();
        s_mapWidth = properties.get("width", Integer.class);
        s_mapHeight = properties.get("height", Integer.class);
        int tileWidth = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);

        if (tileWidth != tileHeight)
        {
            throw new GameException("the tile width and tile height are not equal (" + filename + ")");
        }
        s_tileSize = tileWidth;

        TiledMapTileLayer infoLayer = (TiledMapTileLayer) m_map.getLayers().get("info");
        infoLayer.setVisible(false);

        TiledMapTileSet tileSet  = m_map.getTileSets().getTileSet("info");
        int             firstgid = tileSet.getProperties().get("firstgid", Integer.class);

        for (int x = 0; x < infoLayer.getWidth(); x++)
        {
            for (int y = 0; y < infoLayer.getHeight(); y++)
            {
                TiledMapTileLayer.Cell cell = infoLayer.getCell(x, y);
                if (cell == null) continue;

                int                   id       = cell.getTile().getId() - firstgid;
                TileInfo.TileInfoType infoType = TileInfo.TileInfoType.get(id);
                if (infoType == null) continue;

                entities.add(new InfoTile(x, y, infoType));
            }
        }

        Log.debug(Log.Logger.MAP, String.format("Map successfully loaded (%s)", filename));
    }

    public void render()
    {
        m_mapRenderer.render();
    }

    public void update(OrthographicCamera camera)
    {
        m_mapRenderer.setView(camera);
    }

    @Override
    public void dispose()
    {
        m_map.dispose();
        m_mapRenderer.dispose();
    }

    public static int getMapHeight()
    {
        return s_mapHeight;
    }

    public static int getMapWidth()
    {
        return s_mapWidth;
    }

    public static int getTileSize()
    {
        return s_tileSize;
    }
}
