package com.brandonjja.taskRun.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class ScoreboardTR {
	private Scoreboard board;
	private Objective obj;
	
	public ScoreboardTR(PlayerTR player) {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("taskRun", "dummy");
		obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Task Board");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if (player.getHasScoreboard()) {
			player.getPlayer().setScoreboard(board);
		} else {
			player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		setTasks(player);
	}
	
	private void setTasks(PlayerTR player) {
		int ctr = TaskRun.currentGame.getTotalTasksToFinish(); // null pointer here
		for (TR_Task task : player.getTaskList()) {
			obj.getScore(task.toString()).setScore(ctr--);
		}
		
		ctr = TaskRun.currentGame.getTotalTasksToFinish() + 2;
		
		obj.getScore(" ").setScore(ctr - 1);
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			obj.getScore(ChatColor.AQUA + pl.getName() + " " + ChatColor.YELLOW + TaskRun.getPlayer(pl).getFinishedTasks() + "/" + (TaskRun.currentGame.getTotalTasksToFinish())).setScore(ctr++);
		}
		
		// Code for player name/scores being displayed below task list
		
		/*int ctr = Bukkit.getOnlinePlayers().size();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			obj.getScore(ChatColor.AQUA + pl.getName() + " " + ChatColor.YELLOW + player.getFinishedTasks() + "/" + (TaskRun.currentGame.getTotalTasksToFinish())).setScore(ctr--);
		}
		
		ctr = TaskRun.currentGame.getTotalTasksToFinish() + Bukkit.getOnlinePlayers().size() + 1;
		
		for (TR_Task task : player.getTaskList()) {
			obj.getScore(task.toString()).setScore(ctr--);
		}
		
		obj.getScore(" ").setScore(ctr);*/
	}
	
	public void updateTask(TR_Task task, Player player, int oldScore) {
		Score pos = obj.getScore(task.toString(oldScore));
		int position = pos.getScore();
		board.resetScores(task.toString(oldScore));
		obj.getScore(task.toString()).setScore(position);
		if (task.needToComplete <= task.completed) {
			pos = obj.getScore(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + TaskRun.currentGame.getTotalTasksToFinish());
			position = pos.getScore();
			//board.resetScores(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + TaskRun.currentGame.getTotalTasksToFinish());
			//obj.getScore(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks() + 1) + "/" + TaskRun.currentGame.getTotalTasksToFinish()).setScore(position);
			updateScoreAllPlayers(player, position);
		}
	}
	
	private void updateScoreAllPlayers(Player player, int position) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			PlayerTR trPlayer = TaskRun.getPlayer(pl);
			trPlayer.getBoard().getScoreboard().resetScores(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + TaskRun.currentGame.getTotalTasksToFinish());
			trPlayer.getBoard().getObjective().getScore(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks() + 1) + "/" + TaskRun.currentGame.getTotalTasksToFinish()).setScore(position);
		}
	}
	
	public Scoreboard getScoreboard() {
		return board;
	}
	
	private Objective getObjective() {
		return obj;
	}
}
