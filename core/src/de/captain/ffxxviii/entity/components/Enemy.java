package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;

public class Enemy implements Component
{
    public enum EnemyType
    {
        GOBLIN,;
    }

    public final EnemyType type;

    public Enemy(EnemyType type)
    {
        this.type = type;
    }
}
