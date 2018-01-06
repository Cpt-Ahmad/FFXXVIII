package de.captain.ffxxviii.entity;

import de.captain.ffxxviii.entity.components.Component;
import de.captain.ffxxviii.util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Entity
{
    public enum State
    {
        /**
         * The entity should be updated and rendered
         */
        ACTIVE,

        /**
         * The entity should only be updated and not rendered
         */
        PASSIVE,

        /**
         * The entity should neither be updated nor rendered
         */
        FROZEN,

        /**
         * The entity is not needed anymore and can be deleted
         */
        DEAD,
        ;
    }

    private List<Component> m_components = new ArrayList<Component>();

    public State state = State.FROZEN;

    public final   int ID;

    public Entity()
    {
        ID = Util.generateId();
    }

    /**
     * Adds a new component to the entity. If the component already exists, it removes the old one and adds the new component
     *
     * @param newComponent The new component to add
     */
    public void addComponent(Component newComponent)
    {
        Component component;
        for (Iterator<Component> iter = m_components.iterator(); iter.hasNext(); )
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

    public void removeComponent(Class<? extends Component> componentClass)
    {

        Component component;
        for (Iterator<Component> iter = m_components.iterator(); iter.hasNext(); )
        {
            component = iter.next();
            if (component.getClass() == componentClass)
            {
                iter.remove();
                break;
            }
        }
    }

    /**
     * Return the requested component if available.
     *
     * @param componentClass The class of the component to search for
     * @param <T>            The type to cast the component into
     * @return The requested component in type T if available, otherwise null
     */
    public <T extends Component> T getComponent(Class<T> componentClass)
    {
        for (Component component : m_components)
        {
            if (component.getClass() == componentClass)
            {
                return componentClass.cast(component);
            }
        }
        return null;
    }

    public boolean hasComponent(Class<? extends Component> componentClass)
    {
        for (Component component : m_components)
        {
            if (component.getClass() == componentClass)
            {
                return true;
            }
        }
        return false;
    }

    public void addComponents(Component... components)
    {
        for (Component com : components)
        {
            addComponent(com);
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        return (obj instanceof Entity) && (ID == ((Entity) obj).ID);
    }
}
