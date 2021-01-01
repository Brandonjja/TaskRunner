package com.brandonjja.taskRun.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.brandonjja.taskRun.TaskRun;

public class ScoreboardTR {
	private Scoreboard board;
	private Objective obj;
	
	private final String boardDisplayName = ChatColor.GOLD + "" + ChatColor.BOLD + "Task Board";
	
	public ScoreboardTR(PlayerTR player) {
		board = Bukkit.getScoreboardManager().getNewScoreboard();
		obj = board.registerNewObjective("taskRun", "dummy");
		obj.setDisplayName(boardDisplayName);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if (player.getHasScoreboard()) {
			player.getPlayer().setScoreboard(board);
		} else {
			player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		setTasks(player);
	}
	
	/**
	 * Prints the list of tasks on the Task Board for the first time
	 * 
	 * @param player PlayerTR object of the player who is receiving the scoreboard
	 */
	private void setTasks(PlayerTR player) {
		Game game = TaskRun.currentGame;
		if (game == null) return;
		int ctr = game.getTotalTasksToFinish();
		for (TR_Task task : player.getTaskList()) {
			obj.getScore(task.toString()).setScore(ctr--);
		}
		
		ctr = game.getTotalTasksToFinish() + 2;
		
		obj.getScore(" ").setScore(ctr - 1);
		StringBuilder playerScore;
		for (Player pl : Bukkit.getOnlinePlayers()) {
			playerScore = new StringBuilder("");
			playerScore.append(ChatColor.AQUA).append(pl.getName()).append(" ").append(ChatColor.YELLOW)
					.append(TaskRun.getPlayer(pl).getFinishedTasks()).append("/").append(game.getTotalTasksToFinish());
			obj.getScore(playerScore.toString()).setScore(ctr++);
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
	
	
	/**
	 * Updates the progress of a task on the Task Board. Increments the score
	 * shown to all players if the task gets completed
	 * 
	 * @param task the task to update the progress for
	 * @param player the player to update the score for
	 * @param oldScore the old progress of the task
	 */
	public void updateTask(TR_Task task, Player player, int oldScore) {
		Score pos = obj.getScore(task.toString(oldScore));
		int position = pos.getScore();
		board.resetScores(task.toString(oldScore));
		obj.getScore(task.toString()).setScore(position);
		if (task.needToComplete <= task.completed) {
			pos = obj.getScore(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + TaskRun.currentGame.getTotalTasksToFinish());
			position = pos.getScore();
			updateScoreAllPlayers(player, position);
		}
	}
	
	/**
	 * Updates a single player's score shown to all players, when a task gets marked as finished
	 * 
	 * @param player the player who finished a task
	 * @param position the position on the scoreboard to update
	 */
	private void updateScoreAllPlayers(Player player, int position) {
		Game game = TaskRun.currentGame;
		String resetScore = ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + game.getTotalTasksToFinish();
		String newScore = (ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks() + 1) + "/" + game.getTotalTasksToFinish());
		for (Player pl : Bukkit.getOnlinePlayers()) {
			PlayerTR trPlayer = TaskRun.getPlayer(pl);
			trPlayer.getBoard().getScoreboard().resetScores(resetScore);
			trPlayer.getBoard().getObjective().getScore(newScore).setScore(position);
		}
	}
	
	public Scoreboard getScoreboard() {
		return board;
	}
	
	private Objective getObjective() {
		return obj;
	}
}
