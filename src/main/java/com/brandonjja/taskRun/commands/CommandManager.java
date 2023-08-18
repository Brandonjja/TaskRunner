package com.brandonjja.taskRun.commands;

import java.util.HashMap;
import java.util.Map;

import com.brandonjja.taskRun.game.AutoShutdown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.commands.handler.BackCommand;
import com.brandonjja.taskRun.commands.handler.NewGameCommand;
import com.brandonjja.taskRun.commands.handler.PauseCommand;
import com.brandonjja.taskRun.commands.handler.TasksCommand;
import com.brandonjja.taskRun.commands.handler.ToggleTasks;

public class CommandManager implements CommandExecutor {

	private static final Map<String, TaskRunCommand> COMMANDS = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		return COMMANDS.get(commandLabel).execute((Player) sender, args);
	}
	
	public static void registerCommands() {
		COMMANDS.put("newgame", new NewGameCommand());
		COMMANDS.put("tasks", new TasksCommand());
		COMMANDS.put("toggletasks", new ToggleTasks());
		COMMANDS.put("tt", new ToggleTasks());
		COMMANDS.put("back", new BackCommand());
		COMMANDS.put("pause", new PauseCommand());
		COMMANDS.put("nostop", new AutoShutdown());
		
		for (String cmdLabel : COMMANDS.keySet()) {
			register(cmdLabel, new CommandManager());
		}
	}
	
	private static void register(String cmdLabel, CommandExecutor command) {
		Bukkit.getPluginCommand(cmdLabel).setExecutor(command);
	}
	
}
