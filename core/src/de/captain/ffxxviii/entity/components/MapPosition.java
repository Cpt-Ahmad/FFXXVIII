package de.captain.ffxxviii.entity.components;

public class MapPosition
{
    /**
     * The position of the entity on the map (grid based)
     */
    public int x, y;

    public MapPosition(int x, int y)
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
