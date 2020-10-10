package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
	}
}
