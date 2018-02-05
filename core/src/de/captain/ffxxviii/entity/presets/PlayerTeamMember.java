package de.captain.ffxxviii.entity.presets;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.CombatInfo;
import de.captain.ffxxviii.entity.components.PlayerTeam;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

public class PlayerTeamMember extends Entity
{
    public PlayerTeamMember(boolean isFrontLine)
    {
        super();
        RenderPosition rPos = new RenderPosition();
        PlayerTeam playerTeam = new PlayerTeam();
        TextureContainer texCon    = new TextureContainer(Assets.getTexture(Asset.TEST));
        CombatInfo cInfo = new CombatInfo(isFrontLine, 100);

        addComponents(rPos, playerTeam, texCon, cInfo);
    }
}
