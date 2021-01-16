package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class PlayerAchievementListener implements Listener {
	
	@EventHandler
	public void onAchievement(PlayerAchievementAwardedEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		Achievement achievement = e.getAchievement();
		
		if (achievement == Achievement.NETHER_PORTAL) {
			if (trPlayer.hasEnteredNether()) {
				e.setCancelled(true);
			}
		} else if (achievement == Achievement.GET_BLAZE_ROD) {
			if (trPlayer.hasGottenBlazeRod()) {
				e.setCancelled(true);
			}
		} else if (achievement == Achievement.GET_DIAMONDS) {
			if (trPlayer.hasDiamonds()) {
				e.setCancelled(true);
			}
		}
	}
}
