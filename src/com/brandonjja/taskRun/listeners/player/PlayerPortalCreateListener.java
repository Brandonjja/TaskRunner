package com.brandonjja.taskRun.listeners.player;

import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.StructureGrowEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerPortalCreateListener implements Listener {
	
	@EventHandler
	public void onTravelToNether(PlayerPortalEvent e) {
		if (e.getTo().getWorld().getEnvironment() == Environment.NETHER) {
			Game.completeTask(e.getPlayer(), 1);
		}
	}
	
	@EventHandler
	public void onTreeGrow(StructureGrowEvent e) {
		if (e.getPlayer() != null) {
			Game.completeTask(e.getPlayer(), 2);
		}
	}
}
