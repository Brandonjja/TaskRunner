package com.brandonjja.taskRun;

import java.util.HashMap;
import java.util.Map;

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
	public static Map<String, PlayerTR> playerList;
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
	
	public static void addPlayer(Player player) {
		if (!playerList.containsKey(player.getName())) {
			PlayerTR trPlayer = new PlayerTR(player);
			playerList.put(player.getName(), trPlayer);
		} else {
			PlayerTR trPlayer = getPlayer(player);
			trPlayer.updatePlayer(player);
			trPlayer.setNewScoreboard(false, false);
		}
	}
	
	public static PlayerTR getPlayer(Player player) {
		if (playerList.containsKey(player.getName())) {
			return playerList.get(player.getName());
		}
		return null;
	}
	
	
}
