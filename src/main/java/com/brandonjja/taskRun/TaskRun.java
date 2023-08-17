package com.brandonjja.taskRun;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.brandonjja.taskRun.commands.CommandManager;
import com.brandonjja.taskRun.game.Game;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.listeners.ListenerManager;

public class TaskRun extends JavaPlugin {
	
	private static TaskRun plugin;
	public static Map<UUID, PlayerTR> playerList;
	public static Game currentGame;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		playerList = new HashMap<>();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			addPlayer(pl);
		}
		
		CommandManager.registerCommands();
		ListenerManager.registerListeners();
	}
	
	@Override
	public void onDisable() {
		plugin = null;
		
		playerList.clear();
		playerList = null;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Adds a player to the game player list, and assigns a scoreboard if necessary
	 * 
	 * @param player
	 */
	public static void addPlayer(Player player) {
		if (!playerList.containsKey(player.getUniqueId())) {
			PlayerTR trPlayer;
			if (currentGame != null) {
				trPlayer = new PlayerTR(player, currentGame.getTaskList());
			} else {
				trPlayer = new PlayerTR(player);
			}
			playerList.put(player.getUniqueId(), trPlayer);
		} else {
			PlayerTR trPlayer = getPlayer(player);
			trPlayer.updatePlayer(player);
			trPlayer.setNewScoreboard(false, false);
		}
	}
	
	/**
	 * Retrieves a player from the player list
	 * 
	 * @param player
	 * @return PlayerTR object for the player
	 */
	public static PlayerTR getPlayer(Player player) {
		PlayerTR playerTR = playerList.get(player.getUniqueId());
		if (playerTR == null) {
			addPlayer(player);
			playerTR = playerList.get(player.getUniqueId());
		}

		return playerTR;
	}
	
	/** Removes the Player object from PlayerTR for GC */
	public static void removePlayer(Player player) {
		playerList.get(player.getUniqueId()).updatePlayer(null);
	}
	
	
}
