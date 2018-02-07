package de.captain.ffxxviii.item.components;

import de.captain.ffxxviii.item.Item;
import de.captain.ffxxviii.item.Items;
import de.captain.ffxxviii.util.ImmutableArray;

import java.util.Map;

public class Recipe implements ItemComponent
{
    public final ImmutableArray<UnmodifiableItemStack> itemReq;

    public Recipe(Map itemsReq)
    {
        UnmodifiableItemStack[] stacks = new UnmodifiableItemStack[itemsReq.size()];
        int                     index  = 0;
        for (Object keyObject : itemsReq.keySet())
        {
            String key = (String) keyObject;
            stacks[index++] = new UnmodifiableItemStack(Items.getItem(key), (Integer) itemsReq.get(keyObject));
        }

        itemReq = new ImmutableArray<>(stacks);
    }

    public class UnmodifiableItemStack
    {
        public final Item item;
        public final int  count;

        public UnmodifiableItemStack(Item item, int count)
        {
            this.item = item;
            this.count = count;
        }
    }
}
