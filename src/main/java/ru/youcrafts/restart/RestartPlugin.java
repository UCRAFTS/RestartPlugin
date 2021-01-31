package ru.youcrafts.restart;

import co.aikar.commands.PaperCommandManager;
import co.aikar.commands.annotation.Description;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.scheduler.BukkitScheduler;
import ru.youcrafts.restart.commands.RestartCommand;
import ru.youcrafts.restart.listeners.PlayerCommandPreprocessListener;
import ru.youcrafts.restart.listeners.PlayerLoginListener;
import ru.youcrafts.restart.tasks.RestartTask;

@Plugin(name = "RestartPlugin", version = "1.0.0")
@Author(value = "oDD1 / Alexander Repin")
@Description(value = "Scheduled server restart plugin")
public class RestartPlugin extends JavaPlugin
{

    private static RestartPlugin plugin;
    private static Config config;
    private static PaperCommandManager commandManager;

    public static int restartTaskId;

    @Override
    public void onEnable()
    {
        RestartPlugin.plugin = this;
        RestartPlugin.config = new Config(RestartPlugin.plugin);
        RestartPlugin.commandManager = new PaperCommandManager(RestartPlugin.plugin);
        RestartPlugin.config.init();

        this.registerCommands();
        this.registerTasks();
        this.registerListeners();
    }

    public static RestartPlugin getPlugin()
    {
        return RestartPlugin.plugin;
    }

    private void registerCommands()
    {
        RestartPlugin.commandManager.getCommandReplacements().replace("restart");
        RestartPlugin.commandManager.registerCommand(new RestartCommand(RestartPlugin.config));
    }

    private void registerTasks()
    {
        BukkitScheduler scheduler = getServer().getScheduler();
        RestartPlugin.restartTaskId = scheduler.scheduleAsyncRepeatingTask(this, new RestartTask(RestartPlugin.config, false), 0, 20L);
    }

    private void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(RestartPlugin.config), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
    }
}