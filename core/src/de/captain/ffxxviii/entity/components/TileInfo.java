package de.captain.ffxxviii.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public class TileInfo implements Component
{
    public enum TileInfoType
    {
        INTERACTION(0),
        BLOCKED(1),
        BLOCKED_AND_INTERACTION(2, new TileInfoType[]{INTERACTION, BLOCKED}),
        RERENDER(3);

        public final int                     id;
        public final ImmutableArray<Integer> multipleInfo;

        TileInfoType(int id)
        {
            this(id, null);
        }

        TileInfoType(int id, TileInfoType[] infoTypes)
        {
            this.id = id;

            if (infoTypes == null)
            {
                multipleInfo = null;
            } else
            {
                Integer[] ids = new Integer[infoTypes.length];
                for (int i = 0; i < infoTypes.length; i++)
                {
                    ids[i] = infoTypes[i].id;
                }
                this.multipleInfo = new ImmutableArray<>(new Array<>(ids));
            }
        }

        public static TileInfoType get(int id)
        {
            for (TileInfoType type : values())
            {
                if (type.id == id)
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
