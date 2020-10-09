package com.brandonjja.taskRun.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.commands.handler.BackCommand;
import com.brandonjja.taskRun.commands.handler.NewGameCommand;
import com.brandonjja.taskRun.commands.handler.TasksCommand;
import com.brandonjja.taskRun.commands.handler.ToggleTasks;

public class CommandManager implements CommandExecutor {
	private static Map<String, TaskRunCommand> commandList = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		return commandList.get(commandLabel).execute((Player) sender, args);
	}
	
	public static void registerCommands() {
		commandList.put("newgame", new NewGameCommand());
		commandList.put("tasks", new TasksCommand());
		commandList.put("toggletasks", new ToggleTasks());
		commandList.put("tt", new ToggleTasks());
		commandList.put("back", new BackCommand());
		
		for (String cmdLabel : commandList.keySet()) {
			register(cmdLabel, new CommandManager());
		}
	}
	
	private static void register(String cmdLabel, CommandExecutor command) {
		Bukkit.getPluginCommand(cmdLabel).setExecutor(command);
	}
	
}
