package com.brandonjja.taskRun.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class ScoreboardTR {
	private Scoreboard board;
	private Objective obj;
	
	private static int ctr = 0;
	
	public ScoreboardTR(PlayerTR player) {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("taskRun" + ++ctr, "dummy");
		obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Tasks");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if (player.getHasScoreboard()) {
			player.getPlayer().setScoreboard(board);
		} else {
			player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		setTasks(player);
	}
	
	private void setTasks(PlayerTR player) {
		int ctr = TaskRun.currentGame.getTotalTasksToFinish();
		for (TR_Task task : player.getTaskList()) {
			obj.getScore(task.toString()).setScore(ctr--);
		}
	}
	
	public void updateTask(TR_Task task, int oldScore) {
		Score pos = obj.getScore(task.toString(oldScore));
		int position = pos.getScore();
		board.resetScores(task.toString(oldScore));
		obj.getScore(task.toString()).setScore(position);
	}
	
	public Scoreboard getScoreboard() {
		return board;
	}
}
