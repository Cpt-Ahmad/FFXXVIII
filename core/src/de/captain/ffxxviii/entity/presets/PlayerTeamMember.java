package de.captain.ffxxviii.entity.presets;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.CombatInfo;
import de.captain.ffxxviii.entity.components.Equipment;
import de.captain.ffxxviii.entity.components.RenderPosition;
import de.captain.ffxxviii.entity.components.TextureContainer;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

@Deprecated
public class PlayerTeamMember extends Entity
{
    public PlayerTeamMember()
    {
        super();
        RenderPosition rPos  = new RenderPosition();
        Equipment      equip = new Equipment();

        add(rPos);
        add(equip);
    }

    public Entity melee()
    {
        CombatInfo cInfo = new CombatInfo(true, 200);
        cInfo.isEnemy = false;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.WARRIOR));

        add(cInfo);
        add(texCon);

        return this;
    }

    public Entity ranged()
    {
        CombatInfo cInfo = new CombatInfo(true, 100);
        cInfo.isEnemy = false;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.ARCHER));

        add(cInfo);
        add(texCon);

        return this;
    }

    public Entity mage()
    {
        CombatInfo cInfo = new CombatInfo(true, 100);
        cInfo.isEnemy = false;

        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.MAGE));

        add(cInfo);
        add(texCon);

        return this;
    }
}
