package com.brandonjja.taskRun.listeners.player;

import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.brandonjja.taskRun.TaskRun;

public class PlayerConnectionListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		TaskRun.addPlayer(player);
		if (!NMSUtils.isAtLeastOneTwelve()) {
			player.awardAchievement(Achievement.OPEN_INVENTORY);
			NMSUtils.sendTitleMessage(player, "", "");
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		TaskRun.removePlayer(event.getPlayer());
	}
}
