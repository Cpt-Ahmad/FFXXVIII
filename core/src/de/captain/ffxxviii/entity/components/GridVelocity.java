package de.captain.ffxxviii.entity.components;

import com.badlogic.gdx.math.Vector2;
import de.captain.ffxxviii.main.WorldMap;

/**
 * Component for moving entities inside of a grid map.
 * Once movement started the entity is locked and will ignore all following movement inputs until it is finished moving
 * to the desired map location.
 */
public class GridVelocity implements Component
{
    public enum Direction
    {
        NONE,
        MOVING,
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private boolean m_isLocked    = false;
    private int     m_ticksMoving = 0;
    private int   m_maxTicksForMovement;
    private float m_movementSpeed;

    private Vector2   m_velocity  = new Vector2();
    private Direction m_direction = Direction.NONE;

    /**
     * Sets the maxTicksForMovement variable to 60.
     */
    public GridVelocity()
    {
        this(60);
    }

    /***
     * @param maxTicksForMovement The amount of ticks the movement of the entity takes. Has to be greater than 0, otherwise
     *                            an exception is thrown.
     */
    public GridVelocity(int maxTicksForMovement)
    {
        if (maxTicksForMovement < 1)
        {
            throw new IllegalArgumentException("The maximum ticks for the movement have to greater than 0");
        }

        m_maxTicksForMovement = maxTicksForMovement;
        m_movementSpeed = ((float) WorldMap.getTileSize()) / ((float) maxTicksForMovement);
    }

    /**
     * Sets the movement speed of the component. If the component is locked, i.e. the entity is moving, the movement speed will not
     * be changed.
     *
     * @param movementSpeed the new movement speed for the entity
     * @return True if the velocity could be changed (i.e. the component was not locked), false otherwise (i.e. the
     * component was locked)
     */
    public boolean setMovementSpeed(float movementSpeed)
    {
        if (m_isLocked)
        {
            return false;
        }

        m_movementSpeed = movementSpeed;
        return true;
    }

    /**
     * Sets the direciton of the component. If the component is locked, i.e. the entity is moving, the direction will not
     * be changed.
     *
     * @param direction the new direction for the entity
     * @return True if the direction could be changed (i.e. the component was not locked), false otherwise (i.e. the
     * component was locked)
     */
    public boolean setDirection(Direction direction)
    {
        if (m_isLocked)
        {
            return false;
        }

        if(direction == Direction.MOVING)
        {
            throw new IllegalArgumentException("The direction cannot be set to MOVING");
        }

        m_direction = direction;
        return true;
    }

    /**
     * Increases the movement tick counter, locks the component and checks whether or not the movement is done.
     * This should be called before changing the applying the velocity, as it will set the vector according to the
     * current direction.
     *
     * @return the direction of the current movement when it is finished, otherwise NONE
     */
    public Direction move()
    {
        if(m_direction == Direction.NONE)
        {
            return Direction.NONE;
        }

        if (!m_isLocked)
        {
            m_isLocked = true;
            switch (m_direction)
            {
                case UP:
                    m_velocity.y = m_movementSpeed;
                    break;
                case DOWN:
                    m_velocity.y = -m_movementSpeed;
                    break;
                case LEFT:
                    m_velocity.x = -m_movementSpeed;
                    break;
                case RIGHT:
                    m_velocity.x = m_movementSpeed;
                    break;
            }
        }

        m_ticksMoving++;
        if (m_ticksMoving == m_maxTicksForMovement)
        {
            Direction tmp = m_direction;

            m_ticksMoving = 0;
            m_velocity.set(0f, 0f);
            m_direction = Direction.NONE;
            m_isLocked = false;
            return tmp;
        }
        return Direction.MOVING;
    }

    public void cancelMovement()
    {
        if (!m_isLocked)
        {
            m_direction = Direction.NONE;
        }
    }

    public Direction getDirection()
    {
        return m_direction;
    }

    public Vector2 getVelocity()
    {
        return m_velocity;
    }
}
