package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.game.Game;

public class PlayerInventoryUpdate implements Listener {
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		ItemStack item = e.getItem().getItemStack();
		if (item.getType() == Material.OBSIDIAN) {
			int ctr = item.getAmount();
			do {
				Game.completeTask(e.getPlayer(), 3);
			} while (--ctr > 0);
		} else if (item.getType() == Material.BLAZE_ROD) {
			int ctr = item.getAmount();
			do {
				Game.completeTask(e.getPlayer(), 12);
			} while (--ctr > 0);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		ItemStack item = e.getItemDrop().getItemStack();
		if (item.getType() == Material.OBSIDIAN) {
			Game.removeTaskProgress(e.getPlayer(), 3, item.getAmount());
		} else if (item.getType() == Material.BLAZE_ROD) {
			Game.removeTaskProgress(e.getPlayer(), 12, item.getAmount());
		}
	}

}
