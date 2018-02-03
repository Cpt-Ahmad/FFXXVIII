package de.captain.ffxxviii.entity.components;

public class TileInfo implements Component
{
    public enum TileInfoType
    {
        BLOCKED(0);

        public final int id;

        TileInfoType(int id)
        {
            this.id = id;
        }

        public static TileInfoType get(int id)
        {
            for(TileInfoType type : values())
            {
                if(type.id == id)
                {
                    return type;
                }
            }
            return null;
        }
    }

    public final TileInfoType type;

    public TileInfo(TileInfoType type)
    {
        this.type = type;
    }
}
