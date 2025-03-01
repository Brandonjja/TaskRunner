package com.brandonjja.taskRun.listeners.player;

import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class PlayerTravelToNether implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTravelToNether(PlayerPortalEvent event) {
        if (NMSUtils.isAtLeastOneTwelve()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerTR trPlayer = TaskRun.getPlayer(player);

        // Fixes this achievement not always displaying in chat for players in versions below 1.12. It will not
        // actually award the achievement, but at least players are notified
        if (event.getTo().getWorld().getName().contains("nether") && !player.hasAchievement(Achievement.NETHER_PORTAL) && !trPlayer.hasEnteredNether()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(player.getName() + " has just earned the achievement " + ChatColor.GREEN + "[We Need to Go Deeper]");
            }
        }

        trPlayer.enterNether();
    }
}
