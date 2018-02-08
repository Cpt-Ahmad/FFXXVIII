package de.captain.ffxxviii.entity.presets;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.CombatInfo;
import de.captain.ffxxviii.entity.components.Equipment;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

public class PlayerTeamMember extends Entity
{
    public PlayerTeamMember()
    {
        super();
        RenderPosition   rPos   = new RenderPosition();
        Equipment equip = new Equipment();

        addComponents(rPos,equip);
    }

    public Entity melee()
    {
        CombatInfo cInfo = new CombatInfo(true, 200);
        cInfo.isEnemy = false;
        cInfo.strength = 5;
        cInfo.armor = 20;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.WARRIOR));
        addComponents(cInfo, texCon);
        return this;
    }

    public Entity ranged()
    {
        CombatInfo cInfo = new CombatInfo(true, 100);
        cInfo.isEnemy = false;
        cInfo.strength = 10;
        cInfo.armor = 10;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.ARCHER));
        addComponents(cInfo, texCon);
        return this;
    }

    public Entity mage()
    {
        CombatInfo cInfo = new CombatInfo(true, 100);
        cInfo.isEnemy = false;
        cInfo.strength = 10;
        cInfo.armor = 10;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.MAGE));
        addComponents(cInfo, texCon);
        return this;
    }
}
