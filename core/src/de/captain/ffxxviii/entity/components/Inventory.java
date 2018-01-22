package de.captain.ffxxviii.entity.components;

import de.captain.ffxxviii.items.Item;
import de.captain.ffxxviii.items.components.ItemStack;

import java.util.*;

public class Inventory implements Component
{
    private final List<ItemStack> m_itemStacks = new ArrayList<>();

    public Inventory()
    {
    }

    public Inventory(Collection<ItemStack> collection)
    {
        add(collection);
    }

    /**
     * @return unmodifiable list of item stacks currently in the inventory
     */
    public List<ItemStack> getItemStacks()
    {
        return Collections.unmodifiableList(m_itemStacks);
    }

    /**
     * Adds all items from the given inventory to this inventory. It does not remove the items added to the inventory
     * from the other inventory.
     *
     * @param inventory the inventory to take the items from
     */
    public void add(Inventory inventory)
    {
        add(inventory.getItemStacks());
    }

    /**
     * Adds all the item stacks of the collection to the inventory
     *
     * @param collection has to be a iterable collection
     */
    public void add(Collection<ItemStack> collection)
    {
        for(ItemStack stack : collection)
        {
            add(stack);
        }
    }

    /**
     * Adds the item stack to the inventory
     *
     * @param stack the stack which should be added to the inventory
     */
    public void add(ItemStack stack)
    {
        add(stack.item, stack.count);
    }

    /**
     * Adds the item to the inventory with the given count
     *
     * @param item the item to add
     * @param count the count of the item added
     */
    public void add(Item item, int count)
    {
        if(item == null)
        {
            throw new IllegalArgumentException("The item added to the inventory cannot be null");
        }

        for(ItemStack stack : m_itemStacks)
        {
            if(stack.item.equals(item))
            {
                stack.add(count);
                return;
            }
        }
        m_itemStacks.add(new ItemStack(item, count));
    }

    /**
     * Checks if the there are enough of one item in the inventory.
     *
     * @param item the item to check
     * @param x the required count
     * @return true if there are enough of this item, false otherwise
     */
    public boolean hasEnoughtOfItem(Item item, int x)
    {
        for(ItemStack stack : m_itemStacks)
        {
            if(stack.item.equals(item))
            {
                if(stack.count >= x)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes the specified number of items from the inventory
     *
     * @param stacks the items and counts to remove
     */
    public void removeItems(List<ItemStack> stacks)
    {
        for(ItemStack stack : stacks)
        {
            removeItem(stack.item, stack.count);
        }
    }

    /**
     * Removes one specified item the given amount from the inventory
     *
     * @param item the item to remove
     * @param x the count
     */
    public void removeItem(Item item, int x)
    {
        for(Iterator<ItemStack> iter = m_itemStacks.iterator(); iter.hasNext();)
        {
            ItemStack stack = iter.next();
            if(stack.item.equals(item))
            {
                if(stack.remove(x))
                {
                    iter.remove();
                    return;
                }
            }
        }
    }
}
