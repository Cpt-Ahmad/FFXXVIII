package de.captain.ffxxviii.entity.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public class EntitySystemListener extends EntitySystem implements EntityListener
{
    protected final Array<Entity> m_entities = new Array<>();
    protected final Family        m_family;

    EntitySystemListener(EntitySystemType type, Family family, boolean orderedEntityList)
    {
        super(type.priority);
        m_family = family;
        m_entities.ordered = orderedEntityList;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        ImmutableArray<Entity> tmp = engine.getEntitiesFor(m_family);
        m_entities.addAll(tmp.toArray(), 0, tmp.size());

        engine.addEntityListener(m_family, this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        if(!m_entities.contains(entity, true)) m_entities.add(entity);
    }

    @Override
    public void entityRemoved(Entity entity)
    {
        m_entities.removeValue(entity, true);
    }
}
