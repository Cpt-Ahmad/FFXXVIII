package de.captain.ffxxviii.entity.presets;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

public class Player extends Entity
{
    public Player()
    {
        super();

        TextureContainer texCon    = new TextureContainer(Assets.getAssets().getTexture(Asset.TEST));
        GridPosition     gridPos   = new GridPosition();
        GridVelocity     gridVel   = new GridVelocity(30);
        RenderPosition   renderPos = new RenderPosition();
        Dimension        dim       = new Dimension(texCon.texture.getWidth(), texCon.texture.getHeight());
        PlayerTeam playerTeam = new PlayerTeam();
        CombatInfo cInfo = new CombatInfo(true, 5000);
        Name name = new Name("Player");

        addComponents(gridPos, gridVel, renderPos, dim, texCon, playerTeam, cInfo, name);
    }
}
