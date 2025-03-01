package com.brandonjja.taskRun.listeners.world;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.Month;

public class CreatureSpawnListener implements Listener {

	private static final LocalDateTime CURRENT_DATE = LocalDateTime.now();

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
    @EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		if (CURRENT_DATE.getMonth() != Month.OCTOBER) {
			return;
		}

		if (CURRENT_DATE.getDayOfMonth() != 31) {
			return;
		}

		Entity spawnedEntity = event.getEntity();
		if (spawnedEntity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) spawnedEntity;
			livingEntity.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
			livingEntity.getEquipment().setHelmetDropChance(0);
		}
	}
}
