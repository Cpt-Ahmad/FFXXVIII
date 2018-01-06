package de.captain.ffxxviii.util;

public final class Util
{
    private static int s_idGenerator = 1;

    /**
     * Returns a unique id every time it is called.
     * This method is not thread safe.
     *
     * @return An unique id
     */
    public static int generateId()
    {
        return s_idGenerator++;
    }
}
