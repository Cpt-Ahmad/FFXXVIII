package de.captain.ffxxviii.entity.components;

public class TileInfo implements Component
{
    public enum TileInfoType
    {
        BLOCKED,
        ;
    }

    public final TileInfoType type;

    public TileInfo(TileInfoType type)
    {
        this.type = type;
    }
}
