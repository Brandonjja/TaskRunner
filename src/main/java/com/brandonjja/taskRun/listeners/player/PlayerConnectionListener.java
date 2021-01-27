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
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		TaskRun.addPlayer(player);
		player.awardAchievement(Achievement.OPEN_INVENTORY);
		NMSUtils.sendTitleMessage(player, "", "");
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		TaskRun.removePlayer(e.getPlayer());
	}
}
