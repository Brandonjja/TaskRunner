package com.brandonjja.taskRun.listeners.player;

import java.util.Iterator;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Iterator<ItemStack> it = e.getDrops().iterator();
		while (it.hasNext()) {
			ItemStack stack = it.next();
			if (stack.getType() == Material.BLAZE_ROD) {
				e.getDrops().remove(stack);
				break;
			}
		}
		
		it = e.getDrops().iterator();
		while (it.hasNext()) {
			ItemStack stack = it.next();
			if (stack.getType() == Material.OBSIDIAN) {
				e.getDrops().remove(stack);
				break;
			}
		}
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
	}
}
