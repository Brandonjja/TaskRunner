package com.brandonjja.taskRun.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class TR_Task {
	private static Map<Integer, String> descriptions; // Key = id, String = description of task to do
	private static Map<Integer, Integer> neededCompletions; // Key = id, Value = # of times to complete
	private static int ctr = -1;
	//private static List<Integer> usingIDs = new ArrayList<>();
	
	static {
		descriptions = new HashMap<>();
		neededCompletions = new HashMap<>();
		
		++ctr;
		descriptions.put(ctr, "Make a Bed"); // id = 0
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Travel to The Nether"); // id = 1
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Grow a Tree with Bonemeal"); // id = 2
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Gather 8 Obsidian"); // id = 3
		neededCompletions.put(ctr, 8);
		
		++ctr;
		descriptions.put(ctr, "Kill 5 Zombie Pigmen"); // id = 4
		neededCompletions.put(ctr, 5);
		
		++ctr;
		descriptions.put(ctr, "Catch 2 Fish"); // id = 5
		neededCompletions.put(ctr, 2);
		
		++ctr;
		descriptions.put(ctr, "Milk a Cow"); // id = 6
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Throw 128 Snowballs"); // id = 7
		neededCompletions.put(ctr, 128);
		
		++ctr;
		descriptions.put(ctr, "Stand on Bedrock"); // id = 8
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Build to Height Limit"); // id = 9
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Kill 15 Creepers"); // id = 10
		neededCompletions.put(ctr, 15);
		
		++ctr;
		descriptions.put(ctr, "Build a Snowman"); // id = 11
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Collect 10 Blaze Rods"); // id = 12
		neededCompletions.put(ctr, 10);
		
		++ctr;
		descriptions.put(ctr, "Stand on a Diamond Block"); // id = 13
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Kill a Ghast"); // id = 14
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Shear 10 Sheep"); // id = 15
		neededCompletions.put(ctr, 10);
		
		++ctr;
		descriptions.put(ctr, "Break 20 Glowstone"); // id = 16
		neededCompletions.put(ctr, 20);
		
		++ctr;
		descriptions.put(ctr, "Die from Lava"); // id = 17
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Mine 64 Quartz Ore Blocks"); // id = 18
		neededCompletions.put(ctr, 64);
		
		++ctr;
		descriptions.put(ctr, "Enchant a Golden Shovel"); // id = 19
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Run to X: 512"); // id = 20
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Bake a Cake"); // id = 21
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Eat 10 Raw Chicken"); // id = 22
		neededCompletions.put(ctr, 10);
		
		++ctr;
		descriptions.put(ctr, "Make a Gapple"); // id = 23
		neededCompletions.put(ctr, 1);
		
		++ctr;
		descriptions.put(ctr, "Break 256 Netherrack"); // id = 24
		neededCompletions.put(ctr, 256);
		
	}
	
	int taskID;
	int needToComplete;
	int completed;
	
	String description;
	
	public TR_Task(int taskID) {
		this.taskID = taskID;
		this.needToComplete = neededCompletions.get(taskID);
		this.completed = 0;
		this.description = descriptions.get(taskID);
	}
	
	public TR_Task(TR_Task task) {
		this.taskID = task.taskID;
		this.needToComplete = task.needToComplete;
		this.completed = task.completed;
		this.description = task.description;
	}
	
	@Override
	public String toString() {
		if (completed >= needToComplete) {
			return ChatColor.RED + "" + ChatColor.STRIKETHROUGH + description + " " + "(" + completed + "/" + needToComplete + ")";
		}
		return ChatColor.GREEN + description + " "+ ChatColor.GOLD + "(" + completed + "/" + needToComplete + ")";
	}
	
	public String toString(int completed) {
		if (completed >= needToComplete) {
			return ChatColor.RED + "" + ChatColor.STRIKETHROUGH + description + " " + "(" + completed + "/" + needToComplete + ")";
		}
		return ChatColor.GREEN + description + " "+ ChatColor.GOLD + "(" + completed + "/" + needToComplete + ")";
	}
	
	public int getTaskID() {
		return taskID;
	}
	
	public static int getTotalTaskSize() {
		return descriptions.size();
	}
	
	public void completeTask(PlayerTR player, int id) {
		
		// If the task id is not being used in the current game, return
		if (!TaskRun.currentGame.getIDList().contains(id)) {
			return;
		}
		
		if (isComplete()) {
			return;
		}
		
		if (completed < needToComplete) {
			int oldCompleted = completed;
			completed++;
			player.updateScoreboard(this, oldCompleted);
			if (completed == needToComplete) {
				announceTaskComplete(player.getPlayer(), id);
			}
		}
	}
	
	public void removeTaskProgress(PlayerTR player, int id, int howMuch) {
		
		// If the task id is not being used in the current game, return
		if (!TaskRun.currentGame.getIDList().contains(id)) {
			return;
		}

		if (isComplete()) {
			return;
		}
		
		int oldCompleted = completed;
		
		if (completed > 0) {
			if (completed - howMuch < 0) {
				completed = 0;
			} else {
				completed -= howMuch;
			}
			player.updateScoreboard(this, oldCompleted);
		}
	}
	
	public void announceTaskComplete(Player player, int id) {
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		trPlayer.finishTask();
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN +  " has completed \"" + descriptions.get(id) + "\"");
			pl.sendMessage(ChatColor.GOLD + "Tasks completed: " + trPlayer.getFinishedTasks() + "/" + TaskRun.currentGame.getTotalTasksToFinish());
		}
		trPlayer.checkEndGame();
	}
	
	public boolean isComplete() {
		return completed >= needToComplete;
	}
}
