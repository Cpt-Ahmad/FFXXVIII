package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;

public class Dimension implements Component
{
    public final float width, height;

    public Dimension(float width, float height)
    {
        this.width = width;
        this.height = height;
    }
}
