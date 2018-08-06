package de.captain.ffxxviii.main;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.captain.ffxxviii.entity.components.TileInfo;
import de.captain.ffxxviii.entity.presets.InfoTile;
import de.captain.ffxxviii.util.Log;

import java.util.List;

public class WorldMap implements Disposable
{
    public enum RenderLayer
    {
        BACKGROUND(new int[]{ 0 }, "background"),      // is drawn before everything else
        OBJECT(new int[]{ 1 }, "object")           // draw order is dependent on other objects being in front or behind this object
        ;

        public final int[] layer;
        public final String identifier;

        RenderLayer(int[] l, String id)
        {
            layer = l;
            identifier = id;
        }
    }


    private static int s_tileSize, s_mapWidth, s_mapHeight;

    private TiledMap                   m_map;

    public WorldMap()
    {
    }

    public void load(String filename, Engine engine)
    {
        if(m_map != null) m_map.dispose();

        m_map = new TmxMapLoader().load("maps/" + filename);

        MapProperties properties = m_map.getProperties();
        s_mapWidth = properties.get("width", Integer.class);
        s_mapHeight = properties.get("height", Integer.class);
        int tileWidth  = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);

        if (tileWidth != tileHeight)
        {
            throw new GameException(String.format("the tile width and tile height are not equal (%s)", filename));
        }

        s_tileSize = tileWidth;

        //

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

                if (infoType.multipleInfo == null)
                {
                    engine.addEntity(new InfoTile(x, y, infoType));
                } else
                {
                    for (Integer index : infoType.multipleInfo)
                    {
                        engine.addEntity(new InfoTile(x, y, TileInfo.TileInfoType.get(index)));
                    }
                }
            }
        }
    }

    public TiledMap getMap()
    {
        return m_map;
    }

    @Deprecated
    public void loadMap(String filename, List<Entity> entities)
    {
        if (m_map != null)
        {
            m_map.dispose();
        }
        m_map = new TmxMapLoader().load(filename);

        MapProperties properties = m_map.getProperties();
        s_mapWidth = properties.get("width", Integer.class);
        s_mapHeight = properties.get("height", Integer.class);
        int tileWidth  = properties.get("tilewidth", Integer.class);
        int tileHeight = properties.get("tileheight", Integer.class);

        if (tileWidth != tileHeight)
        {
            throw new GameException(String.format("the tile width and tile height are not equal (%s)", filename));
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

                if (infoType.multipleInfo == null)
                {
                    entities.add(new InfoTile(x, y, infoType));
                } else
                {
                    for (Integer index : infoType.multipleInfo)
                    {
                        entities.add(new InfoTile(x, y, TileInfo.TileInfoType.get(index)));
                    }
                }
            }
        }

        Log.debug(Log.Logger.MAP, String.format("Map successfully loaded (%s)", filename));
    }

    @Override
    public void dispose()
    {
        m_map.dispose();
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
