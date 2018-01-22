package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import de.captain.ffxxviii.items.Item;
import de.captain.ffxxviii.items.components.ItemComponent;
import de.captain.ffxxviii.items.components.Name;
import de.captain.ffxxviii.items.components.Value;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

public final class IOHelper
{
    public static void loadItems(String filename)
    {
        Yaml yaml = new Yaml();
        Map<String, Map> itemData = yaml.load(Gdx.files.internal(filename).readString());
        for(String key : itemData.keySet())
        {
            Map itemProperties = itemData.get(key);
            evaluateItemProperties(key, itemProperties);
        }
    }

    private static void evaluateItemProperties(String key, Map itemProperties)
    {
        List<ItemComponent> components = new ArrayList<>();
        String name = null;
        int value = 1;

        for(Object keyObject : itemProperties.keySet())
        {
            String property = (String) keyObject;

            switch(property)
            {
                case "name":
                    name = (String)itemProperties.get(property);
                    break;
                case "value":
                    value = (Integer)itemProperties.get(property);
            }
        }

        if(name == null)
        {
            throw new MissingResourceException("Item name missing", "String", "name");
        }

        new Item(key, name, value, components);
    }
}
