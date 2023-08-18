package com.brandonjja.taskRun.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.handler.BackCommand;
import com.brandonjja.taskRun.commands.handler.PauseCommand;
import com.brandonjja.taskRun.listeners.player.PlayerAchievementListener;
import com.brandonjja.taskRun.listeners.player.PlayerConnectionListener;
import com.brandonjja.taskRun.listeners.player.PlayerCraftListener;
import com.brandonjja.taskRun.listeners.player.PlayerDeathListener;
import com.brandonjja.taskRun.listeners.player.PlayerInventoryUpdate;
import com.brandonjja.taskRun.listeners.player.PlayerTravelToNether;
import com.brandonjja.taskRun.listeners.tasks.TaskListeners;
import com.brandonjja.taskRun.listeners.world.BlockListener;
import com.brandonjja.taskRun.listeners.world.CreatureSpawnListener;
import com.brandonjja.taskRun.listeners.world.EntityTameListener;

public class ListenerManager {

    public static void registerListeners() {
        register(new PlayerAchievementListener());
        register(new PlayerConnectionListener());
        register(new PlayerCraftListener());
        register(new PlayerDeathListener());
        register(new PlayerInventoryUpdate());
        register(new PlayerTravelToNether());

        register(new TaskListeners());

        register(new BlockListener());
        register(new CreatureSpawnListener());
        register(new EntityTameListener());
        register(new CompassClickListener());

        register(new BackCommand());
        register(new PauseCommand());
    }

    private static void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, TaskRun.getPlugin());
    }
}