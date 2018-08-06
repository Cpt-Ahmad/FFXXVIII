package de.captain.ffxxviii.entity.presets;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.*;
import de.captain.ffxxviii.item.Items;
import de.captain.ffxxviii.main.Asset;
import de.captain.ffxxviii.main.Assets;

public class Goblin extends Entity
{
    public Goblin(float x, float y)
    {
        super();
        add(new Position(x, y));
        add(new Velocity(50f));
        add(new TextureContainer(Assets.getTexture(Asset.GOBLIN)));
    }

    public Goblin(float x, float y, boolean isFrontLine)
    {
        super();


        de.captain.ffxxviii.entity.components.RenderPosition
                rPos = new de.captain.ffxxviii.entity.components.RenderPosition(x, y);
        CombatInfo cInfo = new CombatInfo(isFrontLine, 10);
        cInfo.experiencePoints = 20;
        de.captain.ffxxviii.entity.components.TextureContainer
                texCon = new de.captain.ffxxviii.entity.components.TextureContainer(Assets.getTexture(Asset.GOBLIN));
        Enemy enemy = new Enemy(Enemy.EnemyType.GOBLIN);
        de.captain.ffxxviii.entity.components.Name name =
                new de.captain.ffxxviii.entity.components.Name("Goblin");
        de.captain.ffxxviii.entity.components.Inventory inv = new de.captain.ffxxviii.entity.components.Inventory();
        inv.add(Items.getItem("wood"), 5);

        add(rPos);
        add(cInfo);
        add(texCon);
        add(enemy);
        add(name);
        add(inv);
    }
}
