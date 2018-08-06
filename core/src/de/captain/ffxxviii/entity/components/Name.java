package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;

public class Name implements Component
{
    public final String name;

    public Name(String name)
    {
        this.name = name;
    }
}
