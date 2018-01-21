package de.captain.ffxxviii.entity.components;

import com.badlogic.gdx.graphics.Texture;

public class TextureContainer implements Component {
    public Texture texture;

    public TextureContainer(Texture texture){
        this.texture = texture;
    }

}
