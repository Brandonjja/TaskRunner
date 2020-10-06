package com.brandonjja.taskRun.commands.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;

public class TasksCommand extends TaskRunCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (TaskRun.currentGame == null) {
			player.sendMessage(ChatColor.RED + "No current game");
		} else {
			player.sendMessage(TaskRun.getPlayer(player).getTaskListString());
		}
		return true;
	}
}
