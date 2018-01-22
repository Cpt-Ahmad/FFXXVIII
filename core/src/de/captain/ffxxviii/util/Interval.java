package de.captain.ffxxviii.util;

import java.util.Random;

public class Interval
{
    public final int minimum, maximum;
    public final boolean isMinimumInclusive, isMaximumInclusive;

    private final Random m_random = new Random();

    /**
     * @param minimum            the minimum value of the interval
     * @param maximum            the maximum value of the interval
     * @param isMinimumInclusive is the minimum inclusive in the interval
     * @param isMaximumInclusive is the maximum inclusive in the interval
     * @throws IllegalArgumentException if the minimum is greater then the maximum
     */
    public Interval(int minimum, boolean isMinimumInclusive, int maximum, boolean isMaximumInclusive)
    {
        this.minimum = minimum;
        this.maximum = maximum;
        this.isMinimumInclusive = isMinimumInclusive;
        this.isMaximumInclusive = isMaximumInclusive;

        if (length() < 1)
        {
            throw new IllegalArgumentException(
                    "There has to be at least one number in the interval [min=" + minimum + " (" +
                    (isMinimumInclusive ? "inclusive" : "exclusive") + "); max=" + maximum + " (" +
                    (isMaximumInclusive ? "inclusive" : "exclusive") + ")]");
        }
    }

    /**
     * @return the number of numbers in the interval
     */
    public int length()
    {
        int length = maximum - minimum + 1;
        if (!isMinimumInclusive)
        {
            length -= 1;
        }
        if (!isMaximumInclusive)
        {
            length -= 1;
        }
        return length;
    }

    /**
     * @param x the value to check
     * @return true if x is inside the interval (with respect to the edge cases), otherwise false
     */
    public boolean isInside(int x)
    {
        if ((x > minimum || (isMinimumInclusive && x == minimum)) &&
            (x < maximum || (isMaximumInclusive && x == maximum)))
        {
            return false;
        }
        return true;
    }

    /**
     * @return a random number inside the interval (with respect to the edge cases)
     */
    public int random()
    {
        int bound  = length();
        int offset = minimum;

        if (!isMinimumInclusive)
        {
            offset += 1;
            bound -= 1;
        }
        if (!isMaximumInclusive)
        {
            bound -= 1;
        }

        return m_random.nextInt(bound) + offset;
    }
}
