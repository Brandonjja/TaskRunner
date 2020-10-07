package com.brandonjja.taskRun.listeners.world;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.brandonjja.taskRun.game.Game;

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		if (e.getBlock().getType() == Material.GLOWSTONE) {
			Game.completeTask(player, 16);
		} else if (e.getBlock().getType() == Material.QUARTZ_ORE) {
			Game.completeTask(player, 18);
		}
	}
}
