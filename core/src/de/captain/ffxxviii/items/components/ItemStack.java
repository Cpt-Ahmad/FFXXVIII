package de.captain.ffxxviii.items.components;

import de.captain.ffxxviii.items.Item;

public class ItemStack
{
    private final static int MAX_ITEM_COUNTER = 1_000_000;

    public final Item item;
    public       int  count;

    /**
     * @param item Item of the item stack, cannot be null
     */
    public ItemStack(Item item)
    {
        this(item, 0);
    }


    /**
     * @param item  Item of the item stack, cannot be null
     * @param count Initial count of the item stack
     */
    public ItemStack(Item item, int count)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item of an item stack cannot be null");
        }
        if (count < 0)
        {
            count = 0;
        } else if (count > MAX_ITEM_COUNTER)
        {
            count = MAX_ITEM_COUNTER;
        }

        this.item = item;
        this.count = count;
    }

    /**
     * Adds the given number to the item stack counter. The item stack cannot exceed a total count of 1,000,000 items.
     *
     * @param x the number of items to add to the item stack
     */
    public void add(int x)
    {
        if (count + x > MAX_ITEM_COUNTER)
        {
            count = MAX_ITEM_COUNTER;
        } else
        {
            count += x;
        }
    }

    /**
     * Removes the given number from the item stack counter. The item stack cannot go below a counter of 0 items.
     *
     * @param x the number of items to remove
     * @return true if the stack is empty, eg. the count reaches 0, false otherwise
     */
    public boolean remove(int x)
    {
        if (count - x < 0)
        {
            count = 0;
            return true;
        } else
        {
            count -= x;
            return false;
        }
    }

    /**
     * Sets the counter of the item stack to 0
     */
    public void removeAll()
    {
        count = 0;
    }

    /**
     * Sets the counter of the item stack to the number given. Caps the counter to the minimum/maximum value if necessary
     *
     * @param x the new counter for the item stack
     */
    public void set(int x)
    {
        if (x < 0)
        {
            x = 0;
        } else if (x > MAX_ITEM_COUNTER)
        {
            x = MAX_ITEM_COUNTER;
        } else
        {
            count = x;
        }
    }
}
