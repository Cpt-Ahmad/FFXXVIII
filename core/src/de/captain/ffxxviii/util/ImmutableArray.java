package de.captain.ffxxviii.util;

import java.util.Iterator;

/**
 * This class wraps an array into a class and removes the ability to add or change the data in the array.
 *
 * @param <T> the element of the array
 */
public final class ImmutableArray<T> implements Iterable<T>
{
    private final T[] m_data;

    /**
     * The constructor does not copy the data in the array, instead it just continues to use it.
     * If you want this array and the original to be separated, first clone the original array and then
     * create this immutable array.
     *
     * @param m_data the array to use
     */
    public ImmutableArray(T[] m_data)
    {
        this.m_data = m_data;
    }

    /**
     * Gets the element at the specified position. This method does not explicitly check whether or not
     * the index provided is inbound.
     *
     * @param index the index of the element
     * @return the element at the specified position
     */
    public T get(int index)
    {
        return m_data[index];
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ImmutableArrayIterator();
    }

    public class ImmutableArrayIterator implements Iterator<T>
    {
        private int cursor = 0;

        @Override
        public boolean hasNext()
        {
            return (cursor < m_data.length);
        }

        @Override
        public T next()
        {
            return m_data[cursor++];
        }
    }
}
