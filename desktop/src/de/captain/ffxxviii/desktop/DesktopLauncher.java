package de.captain.ffxxviii.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import de.captain.ffxxviii.main.MainGameClass;

public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        //TexturePacker.process("textures", "packed-textures", "all-textures");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.resizable = false;
        new LwjglApplication(new MainGameClass(), config);
    }
}
