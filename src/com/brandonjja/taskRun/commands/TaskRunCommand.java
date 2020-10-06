package com.brandonjja.taskRun.commands;

import org.bukkit.entity.Player;

public abstract class TaskRunCommand {
	public abstract boolean execute(Player player, String args[]);
}
