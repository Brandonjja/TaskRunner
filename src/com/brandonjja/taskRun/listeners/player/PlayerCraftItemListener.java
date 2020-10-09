package com.brandonjja.taskRun.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.game.Game;

public class PlayerCraftItemListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		ItemStack item = e.getCurrentItem();
		if (item.getType().getId() == 355) {
			
			Game.completeTask((Player) e.getViewers().get(0), 0); // id 0 = Craft a bed
			
			/*if (TaskRun.currentGame == null) {
				return;
			}
			if (TaskRun.currentGame.idListContains(0)) {
				PlayerTR trPlayer = TaskRun.getPlayer((Player) e.getViewers().get(0));
				trPlayer.completeTask(0); // 0 = Craft a bed
			}*/
		} else if (item.getType() == Material.GLOWSTONE) {
			e.setCancelled(true);
			e.getViewers().get(0).sendMessage(ChatColor.YELLOW + "You cannot craft Glowstone in TaskRunner!");
		} else if (item.getType() == Material.CAKE) {
			Game.completeTask((Player) e.getViewers().get(0), 21);
		}
	}
	
	/*@EventHandler
	public void onClick(InventoryClickEvent e) {
		if ((e.getInventory().getType() == InventoryType.CRAFTING || e.getInventory().getType() == InventoryType.WORKBENCH) && e.getSlotType() == SlotType.RESULT) {
			
		}
	}*/
}
