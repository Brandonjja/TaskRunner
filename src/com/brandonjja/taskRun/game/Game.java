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
import com.brandonjja.taskRun.tasks.TR_Task;

public class Game {
	int players;
	private List<Integer> usingIDS;
	private List<TR_Task> taskList;
	private int totalTasksToFinish = 5;
	
	private static int chickenTaskID = -1;
	
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
		
		if (chickenTaskID != -1) {
			Bukkit.getScheduler().cancelTask(chickenTaskID);
		}
		
		chickenTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TaskRun.getPlugin(), new Runnable() {
			@Override
			public void run() {
				Random random = new Random();
				for (Player pl : Bukkit.getOnlinePlayers()) {
					for (Entity entity : pl.getNearbyEntities(20, 20, 20)) {
						if (!(entity.getType().equals(EntityType.CHICKEN))) continue;
						
						Chicken chicken = (Chicken) entity;
						
						if (!chicken.isAdult()) continue;
						
						if (random.nextInt(10) < 4) {
							pl.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.EGG));
							pl.getWorld().playSound(entity.getLocation(), Sound.CHICKEN_EGG_POP, 2, 1);
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
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + "" + ChatColor.ITALIC + " has won the game!");
			pl.sendMessage(ChatColor.YELLOW + "To begin a new game, type " + ChatColor.AQUA + "/newgame");
		}
	}
}
