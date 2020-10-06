package com.brandonjja.taskRun.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.brandonjja.taskRun.TaskRun;

public class PlayerConnectionListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		TaskRun.addPlayer(e.getPlayer());
	}
}
