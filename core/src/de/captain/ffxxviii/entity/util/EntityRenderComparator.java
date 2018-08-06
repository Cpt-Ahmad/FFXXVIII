package de.captain.ffxxviii.entity.util;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.Position;
import de.captain.ffxxviii.entity.components.RenderPosition;

import java.util.Comparator;

public class EntityRenderComparator implements Comparator<Entity>
{
    @Override
    public int compare(Entity o1, Entity o2)
    {
        Position pos1 = o1.getComponent(Position.class);
        Position pos2 = o2.getComponent(Position.class);

        if(pos1.positionVector.y == pos2.positionVector.y)
        {
            return Float.compare(pos1.positionVector.x, pos2.positionVector.x);
        }
        else if(pos1.positionVector.y < pos2.positionVector.y) return -1;
        else return 1;
    }
}
