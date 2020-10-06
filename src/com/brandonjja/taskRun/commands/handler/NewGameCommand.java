package com.brandonjja.taskRun.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import com.brandonjja.taskRun.game.Game;

public class NewGameCommand extends TaskRunCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		TaskRun.currentGame = new Game();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			TaskRun.getPlayer(pl).setNewScoreboard();
		}
		
		return true;
	}
}
