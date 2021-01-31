package ru.youcrafts.restart.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class PlayerCommandPreprocessListener implements Listener
{

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event)
    {
        if (event.getMessage().toLowerCase().equals("/restart")) {
            Bukkit.dispatchCommand(event.getPlayer(), "restartplugin:restart");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event)
    {
        if (event.getCommand().toLowerCase().equals("restart")) {
            Bukkit.dispatchCommand(event.getSender(), "restartplugin:restart");
            event.setCancelled(true);
        }
    }
}
