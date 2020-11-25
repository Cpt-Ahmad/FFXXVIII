package de.captain.ffxxviii.entity.presets;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.ArrowSelection;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

@Deprecated
public class SelectionArrow extends Entity
{
    public SelectionArrow()
    {
        super();
        TextureContainer tex  = new TextureContainer(Assets.getTexture(Asset.ARROW));
        RenderPosition   rPos = new RenderPosition(-100f, -100f);
        ArrowSelection   sel  = new ArrowSelection();

        add(tex);
        add(rPos);
        add(sel);
    }
}
