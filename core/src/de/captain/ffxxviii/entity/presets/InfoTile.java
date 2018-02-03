package de.captain.ffxxviii.entity.presets;

import de.captain.ffxxviii.entity.Entity;
import de.captain.ffxxviii.entity.components.GridPosition;
import de.captain.ffxxviii.entity.components.TileInfo;

public class InfoTile extends Entity
{
    public InfoTile(int x, int y, TileInfo.TileInfoType type)
    {
        GridPosition pos = new GridPosition(x, y);
        TileInfo info = new TileInfo(type);

        addComponents(pos, info);
    }
}
