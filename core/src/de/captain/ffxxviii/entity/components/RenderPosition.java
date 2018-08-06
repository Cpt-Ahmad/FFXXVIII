package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

@Deprecated
public class RenderPosition implements Component
{
    /**
     * The position at which to render the entityold
     */
    public float x, y;

    public RenderPosition()
    {
        this(0f, 0f);
    }

    public RenderPosition(float x, float y)
    {
        set(x, y);
    }

    public void translate(Vector2 velocity)
    {
        translate(velocity.x, velocity.y);
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
