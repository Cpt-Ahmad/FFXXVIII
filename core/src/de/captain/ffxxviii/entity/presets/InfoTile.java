package de.captain.ffxxviii.entity.presets;

import com.badlogic.ashley.core.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.TileInfo;

public class InfoTile extends Entity
{
    public InfoTile(int x, int y, TileInfo.TileInfoType type)
    {
        add(new GridPosition(x, y));
        add(new TileInfo(type));
    }
}
