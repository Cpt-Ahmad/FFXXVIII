package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.TileInfo;
import de.captain.ffxxviii.entity.presets.InfoTile;
import de.captain.ffxxviii.item.Item;
import de.captain.ffxxviii.item.components.ItemComponent;
import de.captain.ffxxviii.item.components.Recipe;
import de.captain.ffxxviii.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

public final class IOHelper
{
    public static void loadItems(String filename)
    {
        Yaml             yaml     = new Yaml();
        Map<String, Map> itemData = yaml.load(Gdx.files.internal(filename).readString());
        for (String key : itemData.keySet())
        {
            Map itemProperties = itemData.get(key);
            evaluateItemProperties(key, itemProperties);
        }
    }

    private static void evaluateItemProperties(String key, Map itemProperties)
    {
        List<ItemComponent> components = new ArrayList<>();
        String              name       = null;
        String              type       = null;
        int                 value      = 1;

        for (Object keyObject : itemProperties.keySet())
        {
            String property = (String) keyObject;

            switch (property)
            {
                case "name":
                    name = (String) itemProperties.get(property);
                    break;
                case "value":
                    value = (Integer) itemProperties.get(property);
                    break;
                case "type":
                    type = (String) itemProperties.get(property);
                    break;
                case "recipe":
                    Map recipeProperty = (Map) itemProperties.get(property);
                    Map itemsReq = (Map) recipeProperty.get("item-req");
                    components.add(new Recipe(itemsReq));
                    break;
                default:
                    Log.log(Log.Logger.ITEM, "The item property \"" + property + "\" does not exist");
                    break;
            }
        }

        if (name == null)
        {
            throw new MissingResourceException("Item name missing", "String", "name");
        }
        if (type == null)
        {
            throw new MissingResourceException("Item type missing", "String", "type");
        }

        new Item(key, name, value, type, components);
    }

    public static TiledMap loadMap(String file, List<Entity> entities)
    {
        TiledMap          map   = new TmxMapLoader().load(file);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("walkable4");
        for (int x = 0; x < layer.getWidth(); x++)
        {
            for (int y = 0; y < layer.getHeight(); y++)
            {
                if (layer.getCell(x, y).getTile().getId() == 20)
                {
                    entities.add(new InfoTile(x, y, TileInfo.TileInfoType.BLOCKED));
                }
            }
        }

        return map;
    }
}
