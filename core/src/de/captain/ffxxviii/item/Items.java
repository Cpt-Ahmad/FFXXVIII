package de.captain.ffxxviii.item;

import com.badlogic.gdx.Gdx;
import de.captain.ffxxviii.item.components.ItemComponent;
import de.captain.ffxxviii.item.components.Recipe;
import de.captain.ffxxviii.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class Items
{
    private static final Map<String, Item> s_itemMap = new HashMap<>();

    public static void init()
    {
        Yaml             yaml     = new Yaml();
        Map<String, Map> itemData = yaml.load(Gdx.files.internal("items.yaml").readString());
        for (String key : itemData.keySet())
        {
            Map itemProperties = itemData.get(key);
            evaluateItemProperties(key, itemProperties);
        }
        Log.debug(Log.Logger.ITEM, s_itemMap.size() + " items loaded");
    }

    private static void evaluateItemProperties(String key, Map itemProperties)
    {
        if (s_itemMap.containsKey(key))
        {
            throw new IllegalArgumentException(String.format("The item identifier \"%s\" already exists", key));
        }

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
                    components.add(new Recipe(recipeProperty));
                    break;
                default:
                    Log.log(Log.Logger.ITEM, String.format("The item property \"%s\" does not exist", property));
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

        s_itemMap.put(key, new Item(key, name, value, type, components));
    }

    public static Item getItem(String identifier)
    {
        if (!s_itemMap.containsKey(identifier))
        {
            throw new IllegalArgumentException("There is no item for the identifier " + identifier);
        }
        return s_itemMap.get(identifier);
    }
}
