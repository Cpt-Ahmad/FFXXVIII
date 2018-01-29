package de.captain.ffxxviii.item;

import de.captain.ffxxviii.item.components.ItemComponent;
import de.captain.ffxxviii.main.IOHelper;
import de.captain.ffxxviii.util.Log;

import java.util.*;

public class Item
{
    private static final Map<String, Item> s_itemMap = new HashMap<>();

    private List<ItemComponent> m_components = new ArrayList<>();
    public final String identifier;
    public final String name;
    public final String type;
    public final int    value;


    public Item(String identifier, String name, int value, String type, List<ItemComponent> components)
    {
        if (identifier == null)
        {
            throw new IllegalArgumentException("Identifier of an item cannot be null");
        }
        if (s_itemMap.containsKey(identifier))
        {
            throw new IllegalArgumentException("Identifier " + identifier + " is already used");
        }

        this.identifier = identifier;
        this.name = name;
        this.value = value;
        this.type = type;

        s_itemMap.put(identifier, this);
        for (ItemComponent component : components)
        {
            this.add(component);
        }
    }

    public static void init()
    {
        IOHelper.loadItems("items.yaml");
        Log.debug(Log.Logger.ITEM, s_itemMap.size() + " items loaded");
    }

    private void add(ItemComponent newComponent)
    {
        ItemComponent component;
        for (Iterator<ItemComponent> iter = m_components.iterator(); iter.hasNext(); )
        {
            component = iter.next();
            if (component.getClass() == newComponent.getClass())
            {
                iter.remove();
                break;
            }
        }
        m_components.add(newComponent);
    }

    public <T extends ItemComponent> T get(Class<T> componentClass)
    {
        for (ItemComponent component : m_components)
        {
            if (component.getClass() == componentClass)
            {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public static Item getItem(String identifier)
    {
        if (!s_itemMap.containsKey(identifier))
        {
            throw new IllegalArgumentException("There is no item for the identifier " + identifier);
        }
        return s_itemMap.get(identifier);
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(identifier, item.identifier);
    }
}
