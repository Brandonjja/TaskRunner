package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class PlayerInventoryUpdate implements Listener {
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		ItemStack item = e.getItem().getItemStack();
		if (item.getType() == Material.OBSIDIAN) {
			int ctr = item.getAmount();
			do {
				trPlayer.completeTask(3);
			} while (--ctr > 0);
		} else if (item.getType() == Material.BLAZE_ROD) {
			int ctr = item.getAmount();
			do {
				trPlayer.completeTask(12);
			} while (--ctr > 0);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		ItemStack item = e.getItemDrop().getItemStack();
		if (item.getType() == Material.OBSIDIAN) {
			trPlayer.removeTaskProgress(3, item.getAmount());
		} else if (item.getType() == Material.BLAZE_ROD) {
			trPlayer.removeTaskProgress(12, item.getAmount());
		}
	}
	
	@EventHandler
	public void onChestItem(InventoryCloseEvent e) {
		/*if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}*/
		//Player player = (Player) e.getPlayer();
	}

}
