package de.captain.ffxxviii.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class RectangleX extends Rectangle
{
    public RectangleX()
    {
    }

    public RectangleX(float x, float y, float width, float height)
    {
        super(x, y, width, height);
    }

    public RectangleX(Rectangle rect)
    {
        super(rect);
    }

    public RectangleX translate(float dx, float dy)
    {
        this.x += dx;
        this.y += dy;

        return this;
    }

    public RectangleX translate(Vector2 vel)
    {
        return translate(vel.x, vel.y);
    }
}
