package de.captain.ffxxviii.entity.components;

public class RenderPosition
{
    /**
     * The position at which to render the entity
     */
    public float x, y;

    public RenderPosition(float x, float y)
    {
        set(x, y);
    }

    public void translate(float dx, float dy)
    {
        x += dx;
        y += dy;
    }

    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
}
