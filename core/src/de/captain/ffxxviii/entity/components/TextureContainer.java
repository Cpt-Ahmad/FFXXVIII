package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureContainer implements Component
{
    public TextureRegion texture;

    public TextureContainer(Texture texture)
    {
        if(texture == null)
        {
            throw new NullPointerException("texture cannot be null");
        }
        this.texture = new TextureRegion(texture);
    }

}
