package de.captain.ffxxviii.item;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import de.captain.ffxxviii.item.components.ItemComponent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class Item
{
    private final ImmutableArray<ItemComponent> m_components;
    public final  String                        identifier;
    public final  String                        name;
    public final  String                        type;
    public final  int                           value;


    public Item(String identifier, String name, int value, String type, Array<ItemComponent> components)
    {
        if (identifier == null)
        {
            throw new IllegalArgumentException("Identifier of an item cannot be null");
        }

        this.identifier = identifier;
        this.name = name;
        this.value = value;
        this.type = type;

        m_components = new ImmutableArray<>(components);
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
