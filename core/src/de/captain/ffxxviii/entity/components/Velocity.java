package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Velocity implements Component
{
    /**
     * A vector with the x and y velocity of the entity. Velocity is given in pixels per second
     */
    public final Vector2 velocityVector = new Vector2();

    /**
     * The speed of the entity in every direction. The velocity vectors length is set to this value
     */
    public float movementSpeed = 1f;

    /**
     * Velocity is set to zero
     *
     * @param speed the movement speed of the entity
     */
    public Velocity(float speed)
    {
        movementSpeed = speed;
    }

    public Velocity(float dx, float dy, float speed)
    {
        movementSpeed = speed;
        setAndNorm(dx, dy);
    }

    public Velocity(Vector2 vec, float speed)
    {
        this(vec.x, vec.y, speed);
    }

    /**
     * sets the new values for the velocity and scales them such that the length of the new velocity vector is
     * equal to the movement speed
     *
     * @param dx the new velocity in x direction
     * @param dy the new velocity in y direction
     */
    public void setAndNorm(float dx, float dy)
    {
        velocityVector.set(dx, dy);
        velocityVector.setLength(movementSpeed);
    }

    public void setLength()
    {
        velocityVector.setLength(movementSpeed);
    }
}
