package com.brandonjja.taskRun.listeners.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.brandonjja.taskRun.game.Game;

public class CreatureSpawnListener implements Listener {
	
	@EventHandler
	public void onBuildSnowman(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (e.getBlock().getType() == Material.PUMPKIN) {
			Location loc = e.getBlock().getLocation();
			if (loc.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK && loc.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK) {
				Game.completeTask(player, 11);
			}
		}
	}
}
