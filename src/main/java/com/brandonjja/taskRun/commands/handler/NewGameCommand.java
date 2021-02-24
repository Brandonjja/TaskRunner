package com.brandonjja.taskRun.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import com.brandonjja.taskRun.game.Game;

public class NewGameCommand extends TaskRunCommand {

	@Override
	public boolean execute(Player player, String[] args) {
		if (!player.hasPermission("taskrunner.newgame")) return true;
		TaskRun.currentGame = new Game();
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			TaskRun.getPlayer(pl).setNewScoreboard(true, false);
			pl.playSound(pl.getLocation(), Sound.WITHER_SPAWN, 1, 1);
		}
		
		return true;
	}
}
