package com.brandonjja.taskRun.game;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoShutdown extends TaskRunCommand {

    private static final String PREFIX = ChatColor.YELLOW + "[AutoReboot] " + ChatColor.ITALIC;

    private static int id = -1;

    public static void shutdown() {
        if (id != -1) {
            System.out.println("Already shutting down");
            return;
        }

        Bukkit.broadcastMessage(PREFIX + "Server will reboot in 1 minute. Run /nostop to cancel!");

        id = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(TaskRun.getPlugin(), 20 * 60).getTaskId();
    }

    @Override
    public boolean execute(Player player, String[] args) {
        if (!player.isOp()) {
            player.sendMessage(PREFIX + "You do not have permission to perform this command!");
            return true;
        }

        if (id == -1) {
            player.sendMessage(PREFIX + "Not scheduled to reboot.");
            return true;
        }

        Bukkit.getScheduler().cancelTask(id);
        id = -1;

        Bukkit.broadcastMessage(PREFIX + "Shutdown cancelled by " + player.getName() + ".");
        player.sendMessage(PREFIX + "Remember to run /stop when you're ready to reboot.");

        return true;
    }
}
