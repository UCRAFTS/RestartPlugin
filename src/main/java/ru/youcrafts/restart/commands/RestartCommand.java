package ru.youcrafts.restart.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitScheduler;
import ru.youcrafts.restart.Config;
import ru.youcrafts.restart.RestartPlugin;
import ru.youcrafts.restart.tasks.RestartTask;
import ru.youcrafts.restart.types.ConfigType;

public class RestartCommand extends BaseCommand
{

    private final Config config;

    public RestartCommand(Config config)
    {
        this.config = config;
    }

    @CommandAlias("restart")
    @CommandPermission("restart")
    @Default
    public void onCommand(CommandSender sender)
    {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.cancelTask(RestartPlugin.restartTaskId);

        RestartPlugin.restartTaskId = scheduler.scheduleAsyncRepeatingTask(RestartPlugin.getPlugin(), new RestartTask(this.config, true), 0, 20L);

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(this.config.getConfig().getString(ConfigType.NOTIFY_SUBTITLE.getName()));
        }
    }
}
