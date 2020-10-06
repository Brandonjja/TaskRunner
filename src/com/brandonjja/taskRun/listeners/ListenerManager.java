package com.brandonjja.taskRun.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.listeners.player.PlayerConnectionListener;
import com.brandonjja.taskRun.listeners.player.PlayerCraftItemListener;
import com.brandonjja.taskRun.listeners.player.PlayerFishingListener;
import com.brandonjja.taskRun.listeners.player.PlayerInteractListener;
import com.brandonjja.taskRun.listeners.player.PlayerInventoryUpdate;
import com.brandonjja.taskRun.listeners.player.PlayerKillEntityListener;
import com.brandonjja.taskRun.listeners.player.PlayerMoveListener;
import com.brandonjja.taskRun.listeners.player.PlayerPortalCreateListener;
import com.brandonjja.taskRun.listeners.world.CreatureSpawnListener;

public class ListenerManager {
	public static void registerListeners() {
		register(new PlayerConnectionListener());
		register(new PlayerCraftItemListener());
		register(new PlayerFishingListener());
		register(new PlayerInteractListener());
		register(new PlayerInventoryUpdate());
		register(new PlayerKillEntityListener());
		register(new PlayerMoveListener());
		register(new PlayerPortalCreateListener());
		
		register(new CreatureSpawnListener());
		register(new CompassClickListener());
	}
	
	private static void register(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, TaskRun.getPlugin());
	}
}