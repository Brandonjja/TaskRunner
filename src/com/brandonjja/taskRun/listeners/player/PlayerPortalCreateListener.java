package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.StructureGrowEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.Game;

public class PlayerPortalCreateListener implements Listener {
	
	@EventHandler
	public void onTravelToNether(PlayerPortalEvent e) {
		if (e.getTo().getWorld().getEnvironment() == Environment.NETHER) {
			Game.completeTask(e.getPlayer(), 1);
		}
		//e.getPlayer().awardAchievement(Achievement.NETHER_PORTAL);
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (e.getTo().getWorld().getName().contains("nether") && !e.getPlayer().hasAchievement(Achievement.NETHER_PORTAL) && !TaskRun.getPlayer(e.getPlayer()).hasEnteredNether()) {
				pl.sendMessage(e.getPlayer().getName() + " has just earned the achievement " + ChatColor.GREEN + "[We Need to Go Deeper]");
				TaskRun.getPlayer(e.getPlayer()).enterNether();
			}
		}
	}
	
	@EventHandler
	public void onTreeGrow(StructureGrowEvent e) {
		if (e.getPlayer() != null) {
			if (e.getBlocks().size() > 1) {
				Game.completeTask(e.getPlayer(), 2);
			}
		}
	}
}
