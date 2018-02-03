package de.captain.ffxxviii.util;

import com.badlogic.gdx.Gdx;

import java.util.List;

public final class Log
{
    public enum Logger
    {
        BATTLE,
        TESTING,
        MAP,
        ITEM
    }

    private static List<Logger> s_logList;

    public static void setLogList(List<Logger> logList)
    {
        s_logList = logList;
    }

    public static void debug(Logger issuer, String msg)
    {
        if (s_logList.contains(issuer))
        {
            Gdx.app.debug(issuer.toString(), msg);
        }
    }

    public static void log(Logger issuer, String msg)
    {
        if (s_logList.contains(issuer))
        {
            Gdx.app.log(issuer.toString(), msg);
        }
    }
}
