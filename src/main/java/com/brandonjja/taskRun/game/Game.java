package com.brandonjja.taskRun.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.nms.NMSUtils;

public class Game {
	int players;
	private List<Integer> usingIDS;
	private List<TR_Task> taskList;
	private int totalTasksToFinish = 5;
	private static Random random = new Random();
	private boolean gameOver;
	
	private static int chickenTaskID = -1;
	
	private final String newGameString = ChatColor.GREEN + "A new " + ChatColor.GOLD + "Task Runner" + ChatColor.GREEN + " game has started. Goodluck!";
	private final String viewTasksString = ChatColor.GREEN + "You can do " + ChatColor.AQUA + "/tasks" + ChatColor.GREEN + " to view your tasks.";
	
	public Game() {
		usingIDS = new ArrayList<>();
		taskList = new ArrayList<>();
		gameOver = false;
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
			pl.sendMessage(newGameString);
			pl.sendMessage(viewTasksString);
			PlayerTR trPlayer = TaskRun.getPlayer(pl);
			trPlayer.setTaskList(taskList);
			
			if (!pl.getInventory().contains(Material.COMPASS)) {
				pl.getInventory().addItem(new ItemStack(Material.COMPASS));
			}
			
		}
		
		if (chickenTaskID != -1) {
			Bukkit.getScheduler().cancelTask(chickenTaskID);
		}
		
		// Increase the drop rate of chickens
		chickenTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TaskRun.getPlugin(), new Runnable() {
			@Override
			public void run() {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					for (Entity entity : pl.getNearbyEntities(30, 30, 30)) {
						if (!(entity.getType().equals(EntityType.CHICKEN))) continue;
						
						Chicken chicken = (Chicken) entity;
						
						if (!chicken.isAdult()) continue;
						
						if (random.nextInt(10) < 1) {
							pl.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.EGG));
							if (NMSUtils.isAtLeastOneNine()) {
								pl.getWorld().playSound(entity.getLocation(), Sound.valueOf("ENTITY_CHICKEN_EGG"), 2, 1);
							} else {
								pl.getWorld().playSound(entity.getLocation(), Sound.CHICKEN_EGG_POP, 2, 1);
							}
						}
						
					}
				}
			}
			
		}, 20 * 30, 20 * 60); // delay, period
		
	}
	
	public List<Integer> getIDList() {
		return usingIDS;
	}
	
	public List<TR_Task> getTaskList() {
		return taskList;
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
		String newGameMsg = ChatColor.YELLOW + "To begin a new game, type " + ChatColor.AQUA + "/newgame";
		if (gameOver) {
			String gameCompletedMsg = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + "" + ChatColor.ITALIC + " has completed all tasks!";
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(gameCompletedMsg);
				pl.sendMessage(newGameMsg);
			}
			return;
		}
		
		gameOver = true;
		
		String gameWonMsg = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + "" + ChatColor.ITALIC + " has won the game!";
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(gameWonMsg);
			pl.sendMessage(newGameMsg);
			if (NMSUtils.isAtLeastOneThirteen()) {
				pl.playSound(pl.getLocation(), Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL"), 1, 1);
			} else if (NMSUtils.isAtLeastOneNine()) {
				pl.playSound(pl.getLocation(), Sound.valueOf("ENTITY_ENDERDRAGON_GROWL"), 1, 1);
			} else {
				pl.playSound(pl.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
			}
			
			sendGameOverTitle(pl, winner);
		}
	}
	
	private void sendGameOverTitle(Player player, Player winner) {
		NMSUtils.sendTitleMessage(player, "Game Over", ChatColor.GOLD + "Winner: " + ChatColor.LIGHT_PURPLE + winner.getPlayer().getName());
	}
	
}
