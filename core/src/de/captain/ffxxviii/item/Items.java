package de.captain.ffxxviii.item;

import com.badlogic.gdx.Gdx;
import de.captain.ffxxviii.entity.type.Element;
import de.captain.ffxxviii.item.components.ItemComponent;
import de.captain.ffxxviii.item.components.Recipe;
import de.captain.ffxxviii.item.components.Weapon;
import de.captain.ffxxviii.main.ItemException;
import de.captain.ffxxviii.util.HashMapWithoutNull;
import de.captain.ffxxviii.util.Log;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, ItemComponent> components = new HashMapWithoutNull<>();
        String                     name       = null;
        String                     type       = null;
        int                        value      = 1;

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
                    components.put(property, new Recipe(recipeProperty));
                    break;
                case "weapon":
                    Map weaponProperty = (Map) itemProperties.get(property);
                    Weapon.WeaponType weaponType =
                            Weapon.WeaponType.valueOf(((String) weaponProperty.get("weapon-type")).toUpperCase());
                    int attackPowerWeapon = (Integer) weaponProperty.get("attack-power");
                    Element elementWeapon = Element.valueOf(((String) weaponProperty.get("element")).toUpperCase());
                    components.put("weapon", new Weapon(attackPowerWeapon, weaponType, elementWeapon));
                    break;
                default:
                    Log.log(Log.Logger.ITEM,
                            String.format("The item property \"%s\" from the item \"%s\" does not exist", property,
                                          name));
                    break;
            }
        }

        if (name == null)
        {
            throw new ItemException("unknown", "name", "the name cannot be null");
        }
        if (type == null)
        {
            throw new ItemException(name, "type", "the type cannot be null");
        }

        // Check if all mandatory item components for this item type are set
        switch (type)
        {
            case "weapon":
                if (components.get("weapon") == null)
                    throw new ItemException(name, "weapon", "the weapon item must have a weapon component");
                break;
        }

        s_itemMap.put(key, new Item(key, name, value, type, components.values()));
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
