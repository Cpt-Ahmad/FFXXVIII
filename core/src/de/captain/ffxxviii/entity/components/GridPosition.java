package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;

import java.util.Objects;

@Deprecated
public class GridPosition implements Component
{
    /**
     * The position of the entity on the map (grid based)
     */
    public int x, y;

    public GridPosition()
    {
        this(0, 0);
    }

    public GridPosition(int x, int y)
    {
        set(x, y);
    }

    public void translate(int dx, int dy)
    {
        x += dx;
        y += dy;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridPosition
                that = (GridPosition) o;
        return x == that.x &&
               y == that.y;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(x, y);
    }

    @Override
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }
}
