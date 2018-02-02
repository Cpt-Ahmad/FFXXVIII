package de.captain.ffxxviii.util;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

public class WorldMap {
    private TiledMap m_map;
    private OrthogonalTiledMapRenderer m_mapRenderer;

    public WorldMap (TiledMap tiledMap, OrthogonalTiledMapRenderer orthogonalTiledMapRenderer){
        m_map = tiledMap;
        m_mapRenderer = orthogonalTiledMapRenderer;
    }

    public TiledMap getMap() {
        return m_map;
    }
}
