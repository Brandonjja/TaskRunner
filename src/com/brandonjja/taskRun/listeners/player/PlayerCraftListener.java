package com.brandonjja.taskRun.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCraftListener implements Listener {
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		ItemStack item = e.getCurrentItem();
		if (item.getType() == Material.GLOWSTONE) {
			e.setCancelled(true);
			e.getViewers().get(0).sendMessage(ChatColor.YELLOW + "You cannot craft Glowstone in TaskRunner!");
		}
	}
}
