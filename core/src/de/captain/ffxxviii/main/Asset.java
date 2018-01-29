package de.captain.ffxxviii.main;

import com.badlogic.gdx.graphics.Texture;

public enum Asset {
    //Enum(Paths);
    TEST("textures/LocaronSouth.png", Texture.class),
    TEST_ENEMY("textures/LocaronArmorSouth.png", Texture.class),
    ARROW("textures/arrow.png", Texture.class),
    ;

    public final String path;
    public final Class assetClass;

    Asset(String s, Class ac){
        path = s;
        assetClass = ac;
    }
}
