package de.captain.ffxxviii.entity.components;

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
}
