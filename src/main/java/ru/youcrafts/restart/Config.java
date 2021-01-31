package ru.youcrafts.restart;

import de.leonhard.storage.Json;
import de.leonhard.storage.internal.FlatFile;
import ru.youcrafts.restart.types.ConfigType;

import java.util.Arrays;

public class Config
{

    private final FlatFile config;

    public Config(RestartPlugin plugin)
    {
        this.config = new Json("config", plugin.getDataFolder().getPath());
    }

    public FlatFile getConfig()
    {
        return this.config;
    }

    public void init()
    {
        this.config.setDefault(ConfigType.NOTIFY_TIME.getName(), "5");
        this.config.setDefault(ConfigType.NOTIFY_TITLE.getName(), "§cВнимание");
        this.config.setDefault(ConfigType.NOTIFY_SUBTITLE.getName(), "§7Рестарт сервера через §f5 §7минут!");
        this.config.setDefault(ConfigType.NOTIFY_COUNTDOWN_SUB_TITLE.getName(), "§7Рестарт сервера через §f%s §7сек.");
        this.config.setDefault(ConfigType.NOTIFY_RESTART_SUB_TITLE.getName(), "§7Рестарт сервера!");
        this.config.setDefault(ConfigType.KICK_MESSAGE.getName(), "§7Рестарт сервера! Пожалуйста, перезайтие через пару минут!");
        this.config.setDefault(ConfigType.RESTART_TIME_LIST.getName(), Arrays.asList(
                "00:00",
                "09:00"
        ));
    }
}
