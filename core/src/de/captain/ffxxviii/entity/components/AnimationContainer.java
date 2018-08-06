package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationContainer implements Component
{
    // TODO
    public Animation<TextureRegion> animation;

    public AnimationContainer(Animation<TextureRegion> anim)
    {
        if(anim == null)
        {
            throw new NullPointerException("animation cannot be null");
        }

        animation = anim;
    }
}
