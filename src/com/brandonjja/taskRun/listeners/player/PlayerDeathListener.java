package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.Game;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.tasks.TR_Task;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getEntity());
		for (TR_Task task : trPlayer.getTaskList()) {
			if (task.getTaskID() == 3 || task.getTaskID() == 12) {
				task.removeTaskProgress(trPlayer.getPlayer(), task.getTaskID(), Integer.MAX_VALUE);
			}
		}
		
		if (e.getDeathMessage().contains("lava")) {
			Game.completeTask(trPlayer.getPlayer(), 17);
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
	}
}
