package ru.youcrafts.restart.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.youcrafts.restart.Config;
import ru.youcrafts.restart.tasks.RestartTask;
import ru.youcrafts.restart.types.ConfigType;

public class PlayerLoginListener implements Listener
{

    private final Config config;

    public PlayerLoginListener(Config config)
    {
        this.config = config;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        if (Bukkit.getServer().hasWhitelist() && RestartTask.isRestartable) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.config.getConfig().getString(ConfigType.KICK_MESSAGE.getName()));
        }
    }
}
