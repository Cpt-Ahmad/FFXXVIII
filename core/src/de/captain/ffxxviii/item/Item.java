package de.captain.ffxxviii.item;

import de.captain.ffxxviii.item.components.ItemComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Item
{
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

        this.identifier = identifier;
        this.name = name;
        this.value = value;
        this.type = type;

        for (ItemComponent component : components)
        {
            this.add(component);
        }
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
