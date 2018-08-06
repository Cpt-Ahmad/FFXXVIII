package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;

/**
 * Marker interface for marking entities which can block others in their movement
 */
public class Blocking implements Component
{
    public final boolean isMovable;

    public Blocking(boolean movable)
    {
        isMovable = movable;
    }
}
