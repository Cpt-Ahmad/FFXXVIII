package de.captain.ffxxviii.main;

public class ItemException extends RuntimeException
{
    public ItemException(String identifier, String msg)
    {
        super(String.format("%s: %s", identifier, msg));
    }

    public ItemException(String identifier, String component, String msg)
    {
        super(String.format("%s (%s): %s", identifier, component, msg));
    }
}
