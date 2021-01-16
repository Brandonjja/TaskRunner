package com.brandonjja.taskRun.game;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;

public class TR_Task {
	private static Map<Integer, String> descriptions; // Key = id, String = description of task to do
	private static Map<Integer, Integer> neededCompletions; // Key = id, Value = # of times to complete
	//private static List<Integer> usingIDs = new ArrayList<>();
	
	// A list of all task IDs, descriptions, and completions needed
	static {
		descriptions = new HashMap<>();
		neededCompletions = new HashMap<>();
		
		descriptions.put(0, "Make a Bed"); // id = 0
		neededCompletions.put(0, 1);
		
		descriptions.put(1, "Travel to The Nether"); // id = 1
		neededCompletions.put(1, 1);
		
		descriptions.put(2, "Grow a Tree with Bonemeal"); // id = 2
		neededCompletions.put(2, 1);
		
		descriptions.put(3, "Gather 8 Obsidian"); // id = 3
		neededCompletions.put(3, 8);
		
		descriptions.put(4, "Kill 5 Zombie Pigmen"); // id = 4
		neededCompletions.put(4, 5);
		
		descriptions.put(5, "Catch 2 Fish"); // id = 5
		neededCompletions.put(5, 2);
		
		descriptions.put(6, "Milk a Cow"); // id = 6
		neededCompletions.put(6, 1);
		
		descriptions.put(7, "Throw 128 Snowballs"); // id = 7
		neededCompletions.put(7, 128);
		
		descriptions.put(8, "Stand on Bedrock"); // id = 8
		neededCompletions.put(8, 1);
		
		descriptions.put(9, "Build to Height Limit"); // id = 9
		neededCompletions.put(9, 1);
		
		descriptions.put(10, "Kill 15 Creepers"); // id = 10
		neededCompletions.put(10, 15);
		
		descriptions.put(11, "Build a Snowman"); // id = 11
		neededCompletions.put(11, 1);
		
		descriptions.put(12, "Collect 10 Blaze Rods"); // id = 12
		neededCompletions.put(12, 10);
		
		descriptions.put(13, "Stand on a Diamond Block"); // id = 13
		neededCompletions.put(13, 1);
		
		descriptions.put(14, "Kill a Ghast"); // id = 14
		neededCompletions.put(14, 1);
		
		descriptions.put(15, "Shear 10 Sheep"); // id = 15
		neededCompletions.put(15, 10);
		
		descriptions.put(16, "Break 20 Glowstone"); // id = 16
		neededCompletions.put(16, 20);
		
		descriptions.put(17, "Die from Lava"); // id = 17
		neededCompletions.put(17, 1);
		
		descriptions.put(18, "Mine 64 Quartz Ore Blocks"); // id = 18
		neededCompletions.put(18, 64);
		
		descriptions.put(19, "Enchant a Golden Shovel"); // id = 19
		neededCompletions.put(19, 1);
		
		descriptions.put(20, "Run to X: 512"); // id = 20
		neededCompletions.put(20, 1);
		
		descriptions.put(21, "Bake a Cake"); // id = 21
		neededCompletions.put(21, 1);
		
		descriptions.put(22, "Eat 10 Raw Chicken"); // id = 22
		neededCompletions.put(22, 10);
		
		descriptions.put(23, "Make a Gapple"); // id = 23
		neededCompletions.put(23, 1);
		
		descriptions.put(24, "Break 256 Netherrack"); // id = 24
		neededCompletions.put(24, 256);
		
		descriptions.put(25, "Light TNT with Flint"); // id = 25
		neededCompletions.put(25, 1);
		
		descriptions.put(26, "Get to XP Level 15"); // id = 26
		neededCompletions.put(26, 1);
		
		descriptions.put(27, "Eat 2 Mushroom Soup"); // id = 27
		neededCompletions.put(27, 2);
		
		descriptions.put(28, "Get to Y: 1 in the Nether"); // id = 28
		neededCompletions.put(28, 1);
		
		descriptions.put(29, "Kill 2 Wither Skeletons"); // id = 29
		neededCompletions.put(29, 2);
		
		descriptions.put(30, "Break 64 Glass"); // id = 30
		neededCompletions.put(30, 64);
		
		descriptions.put(31, "Punch 1024 Grass"); // id = 31
		neededCompletions.put(31, 1024);
		
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
	
	/** Returns the total number of possible tasks */
	public static int getTotalTaskSize() {
		return descriptions.size();
	}
	
	/**
	 * Increases the completion of a task by 1
	 * 
	 * @param player a playerTR instance
	 * @param id the ID number of the task to complete
	 */
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
	
	/**
	 * Decreases the completion of a task by the specified amount
	 * 
	 * @param player a playerTR instance
	 * @param id the ID number of the task to remove progress from
	 * @param howMuch the amount to decrease by
	 */
	public void removeTaskProgress(PlayerTR player, int id, int howMuch) {
		
		// If the task id is not being used in the current game, return
		if (!TaskRun.currentGame.getIDList().contains(id)) {
			return;
		}

		if (isComplete()) {
			return;
		}
		
		int oldCompleted = completed;
		
		// Don't decrease the progress below zero
		if (completed > 0) {
			if (completed - howMuch < 0) {
				completed = 0;
			} else {
				completed -= howMuch;
			}
			player.updateScoreboard(this, oldCompleted);
		}
	}
	
	/**
	 * Displays a message in chat for all players when a player completes a task
	 * 
	 * @param player
	 * @param id
	 */
	public void announceTaskComplete(Player player, int id) {
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		trPlayer.finishTask();
		for (Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN +  " has completed \"" + descriptions.get(id) + "\"");
			pl.sendMessage(ChatColor.GOLD + "Tasks completed: " + trPlayer.getFinishedTasks() + "/" + TaskRun.currentGame.getTotalTasksToFinish());
			pl.playSound(pl.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1, 1);
		}
		trPlayer.checkEndGame();
	}
	
	public boolean isComplete() {
		return completed >= needToComplete;
	}
}
