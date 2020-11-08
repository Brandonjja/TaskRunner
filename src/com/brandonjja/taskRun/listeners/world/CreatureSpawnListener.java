package com.brandonjja.taskRun.listeners.world;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.brandonjja.taskRun.TaskRun;

public class CreatureSpawnListener implements Listener {
	
	@EventHandler
	public void onBuildSnowman(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		if (e.getBlock().getType() == Material.PUMPKIN) {
			Location loc = e.getBlock().getLocation();
			if (loc.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK && loc.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK) {
				TaskRun.getPlayer(player).completeTask(11);
			}
		}
	}
	
	// Halloween Code
	/*@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity en = (LivingEntity) e.getEntity();
			en.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
			en.getEquipment().setHelmetDropChance(0);
		}
	}*/
}
