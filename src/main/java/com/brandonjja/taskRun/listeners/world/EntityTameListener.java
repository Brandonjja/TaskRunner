package com.brandonjja.taskRun.listeners.world;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

public class EntityTameListener implements Listener {
	
	@EventHandler
	public void onWolfTame(EntityTameEvent e) {
		if (e.getEntityType() == EntityType.WOLF) {
			if (e.getOwner() instanceof Player) {
				//Game.completeTask((Player) e.getOwner(), 15);
			}
		}
	}
}
