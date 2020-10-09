package com.brandonjja.taskRun.commands.handler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import com.brandonjja.taskRun.game.PlayerTR;

public class BackCommand extends TaskRunCommand implements Listener {

	@Override
	public boolean execute(Player player, String[] args) {
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (trPlayer.getLocation() != null) {
			Location loc = player.getLocation();
			player.teleport(trPlayer.getLocation());
			TaskRun.getPlayer(player).saveLocation(loc);
			player.sendMessage(ChatColor.YELLOW + "You have been sent to your previous location");
		} else {
			player.sendMessage(ChatColor.RED + "You have no previous location");
		}
		return true;
	}
	
	@EventHandler
	public void onTP(PlayerCommandPreprocessEvent e) {
		Player player = e.getPlayer();
		if (e.getMessage().startsWith("/tp")) {
			String cmd[] = e.getMessage().split(" ");
			
			if (cmd.length == 3 && cmd[2].equalsIgnoreCase(player.getName())) {
				return;
			}
			
			TaskRun.getPlayer(player).saveLocation(player.getLocation());
		}
	}
	
}
