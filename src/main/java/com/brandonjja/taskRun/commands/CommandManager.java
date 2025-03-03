package com.brandonjja.taskRun.commands;

import com.brandonjja.taskRun.commands.handler.BackCommand;
import com.brandonjja.taskRun.commands.handler.NewGameCommand;
import com.brandonjja.taskRun.commands.handler.PauseCommand;
import com.brandonjja.taskRun.commands.handler.TasksCommand;
import com.brandonjja.taskRun.commands.handler.ToggleTasks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

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
        if (!COMMANDS.isEmpty()) {
            return;
        }

        COMMANDS.put("newgame", new NewGameCommand());
        COMMANDS.put("tasks", new TasksCommand());
        COMMANDS.put("toggletasks", new ToggleTasks());
        COMMANDS.put("tt", new ToggleTasks());
        COMMANDS.put("back", new BackCommand());
        COMMANDS.put("pause", new PauseCommand());

        for (String cmdLabel : COMMANDS.keySet()) {
            register(cmdLabel, new CommandManager());
        }
    }

    private static void register(String cmdLabel, CommandExecutor command) {
        Bukkit.getPluginCommand(cmdLabel).setExecutor(command);
    }

}
