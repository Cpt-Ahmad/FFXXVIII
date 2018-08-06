package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Position implements Component
{
    public final Vector2 positionVector = new Vector2();

    public Position()
    {
    }

    public Position(float x, float y)
    {
        positionVector.set(x, y);
    }

    public Position(Vector2 vec)
    {
        positionVector.set(vec);
    }

    public void move(Vector2 velocity, float delta)
    {
        positionVector.mulAdd(velocity, delta);
    }
}
