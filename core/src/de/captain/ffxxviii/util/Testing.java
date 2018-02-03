package de.captain.ffxxviii.util;

public final class Testing
{
    private static Testing s_testing = new Testing();

    private int s_ticks = 0;

    public static Testing get()
    {
        return s_testing;
    }

    public void test()
    {
        s_ticks++;
    }

    public boolean areSixtyTicksOver()
    {
        if (s_ticks > 60)
        {
            s_ticks = 0;
            return true;
        }
        return false;
    }

}
