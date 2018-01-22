package de.captain.ffxxviii.entity.components;

public class Enemy implements Component
{
    public enum EnemyType
    {
        GOBLIN,
        ;
    }

    public final EnemyType type;

    public Enemy(EnemyType type)
    {
        this.type = type;
    }
}
