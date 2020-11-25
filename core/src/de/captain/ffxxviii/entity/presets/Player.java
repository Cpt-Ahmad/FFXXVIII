package de.captain.ffxxviii.entity.presets;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.item.Items;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;
import de.captain.ffxxviii.util.RectangleX;

public class Player extends Entity
{
    public Player()
    {
        TextureContainer texCon    = new TextureContainer(Assets.getTexture(Asset.TEST));
        //GridPosition     gridPos   = new GridPosition();
        //GridVelocity     gridVel   = new GridVelocity(30);
        //RenderPosition   renderPos = new RenderPosition();
        CombatInfo       cInfo     = new CombatInfo(true, 5000);
        Name             name      = new Name("Player");
        Inventory        inv       = new Inventory();
        inv.add(Items.getItem("wood"), 10);
        inv.add(Items.getItem("iron"), 14);
        //PlayerTeam playerTeam = new PlayerTeam(new PlayerTeamMember().melee(), new PlayerTeamMember().ranged(),
        //                                         new PlayerTeamMember().mage());
        Position pos      = new Position();
        Velocity velocity = new Velocity(300f);
        RectangleX hitboxRect =
                new RectangleX(pos.positionVector.x, pos.positionVector.y, texCon.texture.getRegionWidth(),
                               texCon.texture.getRegionHeight());
        Hitbox.HitboxUsage[] hitboxUsages = {Hitbox.HitboxUsage.MOVEMENT, Hitbox.HitboxUsage.DEFENSE};
        Hitbox               hitbox       = new Hitbox(hitboxRect, hitboxUsages);

        add(pos);
        add(velocity);
        //add(gridPos);
        //add(gridVel);
        add(texCon);
        //add(renderPos);
        add(cInfo);
        add(name);
        add(inv);
        //add(playerTeam);
        add(hitbox);
    }
}
