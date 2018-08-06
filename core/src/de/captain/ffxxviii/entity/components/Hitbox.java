package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import de.captain.ffxxviii.util.RectangleX;

public class Hitbox implements Component
{
    public enum HitboxUsage
    {
        MOVEMENT,
        // can be blocked in his way by other hitboxes
        ATTACK,
        // can damage other entities
        DEFENSE        // can be damaged by other entities
    }

    public final ImmutableArray<HitboxUsage> usages;

    public final RectangleX hitbox;

    public Hitbox(RectangleX hb, HitboxUsage[] hu)
    {
        hitbox = hb;
        usages = new ImmutableArray<>(new Array<>(hu));
    }

    public boolean intersects(Hitbox other)
    {
        return hitbox.overlaps(other.hitbox);
    }

    public void setPosition(float x, float y)
    {
        hitbox.setPosition(x, y);
    }

    public void move(float dx, float dy)
    {
        setPosition(hitbox.x + dx, hitbox.y + dy);
    }

    public void move(Vector2 vel)
    {
        move(vel.x, vel.y);
    }
}
