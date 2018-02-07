package de.captain.ffxxviii.entity.presets;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.item.Items;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

public class Goblin extends Entity
{
    public Goblin(float x, float y, boolean isFrontLine)
    {
        super();
        RenderPosition rPos  = new RenderPosition(x, y);
        CombatInfo     cInfo = new CombatInfo(isFrontLine, 10);
        cInfo.experiencePoints = 20;
        TextureContainer texCon = new TextureContainer(Assets.getTexture(Asset.TEST_ENEMY));
        Enemy            enemy  = new Enemy(Enemy.EnemyType.GOBLIN);
        Name             name   = new Name("Goblin");
        Inventory        inv    = new Inventory();
        inv.add(Items.getItem("wood"), 5);

        addComponents(rPos, cInfo, texCon, enemy, name, inv);
    }
}
