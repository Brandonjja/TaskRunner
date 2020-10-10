package com.brandonjja.taskRun.listeners.world;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class BlockListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (e.getBlock().getType() == Material.GLOWSTONE) {
			trPlayer.completeTask(16);
		} else if (e.getBlock().getType() == Material.QUARTZ_ORE) {
			trPlayer.completeTask(18);
		}
	}
}
