package com.brandonjja.taskRun.commands.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import com.brandonjja.taskRun.game.PlayerTR;

public class ToggleTasks extends TaskRunCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		
		if (TaskRun.currentGame == null) {
			player.sendMessage(ChatColor.RED + "No current game");
			return true;
		}
		
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		/*if (!trPlayer.getHasScoreboard()) {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		} else {
			trPlayer.setNewScoreboard();
		}*/
		
		trPlayer.toggleScoreboard();
		trPlayer.setNewScoreboard(false, true);
		
		//trPlayer.toggleScoreboard();
		if (trPlayer.getHasScoreboard()) {
			player.sendMessage(ChatColor.YELLOW + "Task bar toggled on. " + ChatColor.AQUA + "/toggletasks" + ChatColor.YELLOW + " to toggle off.");
		} else {
			player.sendMessage(ChatColor.YELLOW + "Task bar toggled off. " + ChatColor.AQUA + "/toggletasks" + ChatColor.YELLOW + " to toggle on.");
		}
		return true;
	}
}
