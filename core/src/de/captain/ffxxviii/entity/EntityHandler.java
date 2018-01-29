package de.captain.ffxxviii.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.istack.internal.NotNull;
import de.captain.ffxxviii.entity.components.Component;
import de.captain.ffxxviii.entity.systems.EntityRenderSystem;
import de.captain.ffxxviii.entity.systems.EntitySystem;
import de.captain.ffxxviii.entity.systems.EntityUpdateSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EntityHandler
{
    private List<Entity>             m_entities      = new ArrayList<>();
    private List<EntityUpdateSystem> m_updateSystems = new ArrayList<>();
    private List<EntityRenderSystem> m_renderSystems = new ArrayList<>();

    public void update()
    {
        for (EntityUpdateSystem system : m_updateSystems)
        {
            system.update(m_entities);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer)
    {
        for (EntityRenderSystem system : m_renderSystems)
        {
            system.render(batch, shapeRenderer, m_entities);
        }
    }

    public void addEntity(@NotNull Entity entity)
    {
        if (entity == null)
        {
            throw new IllegalArgumentException("Entity should not be null");
        }

        if (!m_entities.contains(entity))
        {
            m_entities.add(entity);
        }
    }

    public void removeEntity(Entity entity)
    {
        m_entities.remove(entity);
    }

    public void addSystem(@NotNull EntitySystem system)
    {
        if (system == null)
        {
            throw new IllegalArgumentException("Entity system should not be null");
        }

        if (system.entitySystemType.isUpdateSystem)
        {
            int index = getIndexForRightPriority(system.entitySystemType, m_updateSystems);
            if (index != -1)
            {
                m_updateSystems.add(index, (EntityUpdateSystem) system);
            } else
            {
                Gdx.app.debug("EntityHandler", "Update system already exists");
            }
        } else
        {
            int index = getIndexForRightPriority(system.entitySystemType, m_renderSystems);
            if (index != -1)
            {
                m_renderSystems.add(index, (EntityRenderSystem) system);
            } else
            {
                Gdx.app.debug("EntityHandler", "Render system already exists");
            }
        }
    }

    public void removeSystem(EntitySystem.EntitySystemType type)
    {
        if (type.isUpdateSystem)
        {
            m_updateSystems.removeIf(entityUpdateSystem -> entityUpdateSystem.entitySystemType == type);
        } else
        {
            m_renderSystems.removeIf(entityRenderSystem -> entityRenderSystem.entitySystemType == type);
        }
    }

    private int getIndexForRightPriority(EntitySystem.EntitySystemType type, @NotNull List<? extends EntitySystem> list)
    {
        int index = -1;
        for (int i = 0; i < list.size(); i++)
        {
            EntitySystem.EntitySystemType existingType = list.get(i).entitySystemType;
            if (existingType == type)
            {
                return -1;
            }
            if (index == -1 && existingType.priority > type.priority)
            {
                index = i;
            }
        }
        return index == -1 ? list.size() : index;
    }

    public void addEntities(Collection<Entity> entities)
    {
        for (Entity entity : entities)
        {
            addEntity(entity);
        }
    }

    public void addEntities(Entity[] entities)
    {
        for (Entity entity : entities)
        {
            addEntity(entity);
        }
    }

    public List<Entity> getEntities(Class<? extends Component>... comClass)
    {
        List<Entity> entities = new ArrayList<>();

        entityLoop:
        for (Entity entity : m_entities)
        {
            for (Class<? extends Component> com : comClass)
            {
                if (!entity.hasComponent(com))
                {
                    continue entityLoop;
                }
            }
            entities.add(entity);
        }

        return entities;
    }

    public int size()
    {
        return m_entities.size();
    }
}
