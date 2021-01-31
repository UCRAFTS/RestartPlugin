package ru.youcrafts.restart.types;

import org.jetbrains.annotations.NotNull;

public enum ConfigType
{

    NOTIFY_TIME("notifyTime"),
    NOTIFY_TITLE("notifyTitle"),
    NOTIFY_SUBTITLE("notifySubTitle"),
    NOTIFY_COUNTDOWN_SUB_TITLE("notifyCountDownSubTitle"),
    NOTIFY_RESTART_SUB_TITLE("notifyRestartSubTitle"),
    KICK_MESSAGE("kickMessage"),
    RESTART_TIME_LIST("restartTimeList");

    private final String name;

    ConfigType(@NotNull final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
