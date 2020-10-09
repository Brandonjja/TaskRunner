package com.brandonjja.taskRun.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.tasks.TR_Task;

public class Game {
	int players;
	private List<Integer> usingIDS;
	private List<TR_Task> taskList;
	private int totalTasksToFinish = 10;
	
	public static void completeTask(Player player, int id) {
		Game game = TaskRun.currentGame;
		if (game == null) {
			return;
		}
		
		if (game.idListContains(id)) {
			PlayerTR trPlayer = TaskRun.getPlayer(player);
			trPlayer.completeTask(id);
		}
	}
	
	public static void removeTaskProgress(Player player, int id, int howMuch) {
		Game game = TaskRun.currentGame;
		if (game == null) {
			return;
		}
		
		if (game.idListContains(id)) {
			PlayerTR trPlayer = TaskRun.getPlayer(player);
			trPlayer.removeTaskProgress(id, howMuch);
		}
	}
	
	public Game() {
		usingIDS = new ArrayList<>();
		taskList = new ArrayList<>();
		while (usingIDS.size() < totalTasksToFinish) {
			int id;
			Random random = new Random();
			
			do {
				id = random.nextInt(TR_Task.getTotalTaskSize());
			} while (usingIDS.contains(id));
			
			usingIDS.add(id);
			taskList.add(new TR_Task(id));
		}
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.GREEN + "A new " + ChatColor.GOLD + "Task Runner" + ChatColor.GREEN + " game has started. Goodluck!");
			pl.sendMessage(ChatColor.GREEN + "You can do " + ChatColor.AQUA + "/tasks" + ChatColor.GREEN + " to view your tasks.");
			PlayerTR trPlayer = TaskRun.getPlayer(pl);
			trPlayer.setTaskList(taskList);
			
			if (!pl.getInventory().contains(Material.COMPASS)) {
				pl.getInventory().addItem(new ItemStack(Material.COMPASS));
			}
			
		}
		
	}
	
	public List<Integer> getIDList() {
		return usingIDS;
	}
	
	public boolean idListContains(int id) {
		if (usingIDS == null) {
			return false;
		}
		return usingIDS.contains(id);
	}
	
	public int getTotalTasksToFinish() {
		return totalTasksToFinish;
	}
	
	public void triggerEndGame(Player winner) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + "" + ChatColor.ITALIC + " has won the game!");
			pl.sendMessage(ChatColor.YELLOW + "To begin a new game, type " + ChatColor.AQUA + "/newgame");
		}
	}
}
