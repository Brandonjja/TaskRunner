package com.brandonjja.taskRun.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class PlayerTradeListener implements Listener {
	
	@EventHandler
	public void onTrade(InventoryOpenEvent e) {
		if (e.getInventory().getType() == InventoryType.MERCHANT) {
			// TODO
		}
	}
}
