package ru.youcrafts.restart;

import org.bukkit.entity.Player;
import java.util.Collection;

public class Utils
{

    public static void sendTitles(Collection<? extends Player> players, String title, String subtitle, int fadeIn, int duration, int fadeOut)
    {
        for (Player player : players) {
            player.sendTitle(title, subtitle, fadeIn, duration, fadeOut);
        }
    }
}
