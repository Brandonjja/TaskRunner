package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerEnchantIListener implements Listener {
	
	@EventHandler
	public void onEnchantHoe(EnchantItemEvent e) {
		if (e.getItem().getType() == Material.GOLD_SPADE) {
			Game.completeTask(e.getEnchanter(), 19);
		}
	}
}
