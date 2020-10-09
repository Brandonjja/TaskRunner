package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerMoveListener implements Listener {
	
	@EventHandler
	public void onStandOnBedrock(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.BEDROCK) {
			Game.completeTask(player, 8);
		} else if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
			Game.completeTask(player, 13);
		}
	}
	
	@EventHandler
	public void onBuildLimitReached(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (player.getLocation().getY() > 256.5) {
			Game.completeTask(player, 9);
		}
	}
	
	@EventHandler
	public void onRunXBlocks(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		if (player.getLocation().getX() > 512) {
			Game.completeTask(player, 20);
		}
	}
}
