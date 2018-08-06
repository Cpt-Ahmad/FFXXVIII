package de.captain.ffxxviii.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapWithoutNull<K, V> extends HashMap<K, V>
{
    public HashMapWithoutNull(int initialCapacity, float loadFactor)
    {
        super(initialCapacity, loadFactor);
    }

    public HashMapWithoutNull(int initialCapacity)
    {
        super(initialCapacity);
    }

    public HashMapWithoutNull()
    {
        super();
    }

    public HashMapWithoutNull(Map<? extends K, ? extends V> m)
    {
        super(m);
    }

    @Override
    public V put(K key, V value)
    {
        if (value == null) throw new IllegalArgumentException("value of this map cannot be null");
        return super.put(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        if (newValue == null) throw new IllegalArgumentException("value of this map cannot be null");
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value)
    {
        if (value == null) throw new IllegalArgumentException("value of this map cannot be null");
        return super.replace(key, value);
    }
}
