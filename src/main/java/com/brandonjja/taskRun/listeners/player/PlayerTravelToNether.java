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
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onTravelToNether(PlayerPortalEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		//e.getPlayer().awardAchievement(Achievement.NETHER_PORTAL);

		if (NMSUtils.isAtLeastOneTwelve()) {
			return;
		}
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (e.getTo().getWorld().getName().contains("nether") && !player.hasAchievement(Achievement.NETHER_PORTAL) && !trPlayer.hasEnteredNether()) {
				pl.sendMessage(player.getName() + " has just earned the achievement " + ChatColor.GREEN + "[We Need to Go Deeper]");
			}
		}
		
		trPlayer.enterNether();
	}
}
