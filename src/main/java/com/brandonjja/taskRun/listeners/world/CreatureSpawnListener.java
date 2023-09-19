package com.brandonjja.taskRun.listeners.world;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CreatureSpawnListener implements Listener {

	@EventHandler
	public void onBuildSnowman(BlockPlaceEvent event) {
		if (event.getBlock().getType() != Material.PUMPKIN) {
			return;
		}

		Location blockLocation = event.getBlock().getLocation();
		if (blockLocation.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK && blockLocation.add(0, -1, 0).getBlock().getType() == Material.SNOW_BLOCK) {
			TaskRun.getPlayer(event.getPlayer()).completeTask(Task.BUILD_A_SNOWMAN);
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
