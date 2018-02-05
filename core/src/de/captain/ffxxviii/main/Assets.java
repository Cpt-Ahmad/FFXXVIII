package de.captain.ffxxviii.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Assets
{
    private final static AssetManager s_assetManager = new AssetManager();
    private final static Skin         s_skin         = new Skin();

    private Assets()
    {
    }

    public static void init()
    {
        for (Asset asset : Asset.values())
        {
            s_assetManager.load(asset.path, asset.assetClass);
        }
        s_assetManager.finishLoading();

        FreeTypeFontGenerator
                generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MontserratAlternates-Bold.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameterBigFont =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameterSmallFont =
                new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameterSmallFont.size = 18;
        parameterBigFont.size = 36;

        BitmapFont fontBig   = generator.generateFont(parameterBigFont);
        BitmapFont fontSmall = generator.generateFont(parameterSmallFont);

        generator.dispose();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        s_skin.add("white", new Texture(pixmap));
        s_skin.add("big", fontBig);
        s_skin.add("small", fontSmall);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = s_skin.newDrawable("white", Color.MAGENTA);
        textButtonStyle.down = s_skin.newDrawable("white", Color.RED);
        //textButtonStyle.checked = s_skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = s_skin.newDrawable("white", Color.YELLOW);
        textButtonStyle.font = s_skin.getFont("big");
        s_skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyleBig = new Label.LabelStyle();
        labelStyleBig.font = s_skin.getFont("big");
        s_skin.add("big", labelStyleBig);

        Label.LabelStyle labelStyleSmall = new Label.LabelStyle();
        labelStyleSmall.font = s_skin.getFont("small");
        s_skin.add("default", labelStyleSmall, Label.LabelStyle.class);

        List.ListStyle listStyle = new List.ListStyle();
        listStyle.font = s_skin.getFont("small");
        listStyle.selection = s_skin.newDrawable("white", Color.RED);
        s_skin.add("default", listStyle, List.ListStyle.class);
    }

    public static Texture getTexture(Asset asset)
    {
        if (asset.assetClass != Texture.class)
        {
            throw new IllegalArgumentException("The asset has to be a texture");
        }

        return s_assetManager.get(asset.path, Texture.class);
    }

    public static Skin getSkin()
    {
        return s_skin;
    }
}

