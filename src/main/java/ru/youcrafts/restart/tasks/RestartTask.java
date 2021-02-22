package ru.youcrafts.restart.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import ru.youcrafts.restart.Config;
import ru.youcrafts.restart.RestartPlugin;
import ru.youcrafts.restart.Utils;
import ru.youcrafts.restart.types.ConfigType;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RestartTask extends BukkitRunnable
{

    private final Config config;
    private int counter;
    private final boolean force;

    public static boolean isRestartable = false;

    public RestartTask(Config config, boolean force)
    {
        this.config = config;
        this.counter = this.config.getConfig().getInt(ConfigType.NOTIFY_TIME.getName()) * 60;
        this.force = force;
    }

    @Override
    public void run()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String timeNow = format.format(date);
        List<String> restartTimeList = this.config.getConfig().getStringList(ConfigType.RESTART_TIME_LIST.getName());
        String notifyTitle = this.config.getConfig().getString(ConfigType.NOTIFY_TITLE.getName());
        String notifySubTitle = this.config.getConfig().getString(ConfigType.NOTIFY_SUBTITLE.getName());
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        if (this.force && !RestartTask.isRestartable) {
            RestartTask.isRestartable = true;

            Utils.sendTitles(players, notifyTitle, notifySubTitle, 20, 60, 20);
        }

        if (!RestartTask.isRestartable) {
            for (String restartTime : restartTimeList) {
                if (timeNow.equals(restartTime)) {
                    RestartTask.isRestartable = true;

                    Utils.sendTitles(players, notifyTitle, notifySubTitle, 20, 60, 20);
                    break;
                }
            }
        }

        if (RestartTask.isRestartable) {
            this.counter--;
        } else {
            return;
        }

        if (this.counter == 60 || (this.counter <= 10 && this.counter >= 0)) {
            notifySubTitle = String.format(this.config.getConfig().getString(ConfigType.NOTIFY_COUNTDOWN_SUB_TITLE.getName()), this.counter);
            Utils.sendTitles(players, notifyTitle, notifySubTitle, 20, 60, 20);
        }

        if (this.counter == 0) {
            notifySubTitle = this.config.getConfig().getString(ConfigType.NOTIFY_RESTART_SUB_TITLE.getName());
            Utils.sendTitles(players, notifyTitle, notifySubTitle, 20, 60, 20);

            Bukkit.getServer().getScheduler().runTaskLater(RestartPlugin.getPlugin(), () -> {
                Bukkit.setWhitelist(true);
                List<String> restartCommands = this.config.getConfig().getStringList(ConfigType.COMMANDS_BEFORE_RESTART.getName());

                for (String restartCommand : restartCommands) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), restartCommand);
                }

                String kickMessage = this.config.getConfig().getString(ConfigType.KICK_MESSAGE.getName());

                for (Player player : players) {
                    player.closeInventory();
                    player.saveData();
                    player.kickPlayer(kickMessage);
                }

                @NotNull List<World> worlds = Bukkit.getWorlds();

                for (World world : worlds) {
                    world.save();
                }

                Bukkit.setWhitelist(false);
                Bukkit.getServer().shutdown();
            }, 40);
        }
    }
}
