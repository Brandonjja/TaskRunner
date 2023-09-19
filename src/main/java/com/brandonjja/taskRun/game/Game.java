package com.brandonjja.taskRun.game;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private static final String NEW_GAME_MESSAGE = ChatColor.GREEN + "A new " + ChatColor.GOLD + "Task Runner" + ChatColor.GREEN + " game has started. Good luck!";
    private static final String VIEW_TASKS_MESSAGE = ChatColor.GREEN + "You can do " + ChatColor.AQUA + "/tasks" + ChatColor.GREEN + " to view your tasks.";

    private static int chickenTaskID = -1;

    private final List<Task> taskList = new ArrayList<>();
    private final int totalTasksToFinish = 5;
    private boolean gameOver = false;

    public Game() {
        List<Task> allTasks = new ArrayList<>(Arrays.asList(Task.VALUES));
        Collections.shuffle(allTasks);
        for (int i = 0; i < totalTasksToFinish; i++) {
            taskList.add(allTasks.get(i));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(NEW_GAME_MESSAGE);
            player.sendMessage(VIEW_TASKS_MESSAGE);
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.setTaskList(taskList);

            if (!player.getInventory().contains(Material.COMPASS)) {
                player.getInventory().addItem(new ItemStack(Material.COMPASS));
            }
        }

        if (chickenTaskID != -1) {
            Bukkit.getScheduler().cancelTask(chickenTaskID);
        }

        // Increase the drop rate of chicken eggs
        chickenTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TaskRun.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (Entity entity : player.getNearbyEntities(30, 30, 30)) {
                    if (!(entity.getType().equals(EntityType.CHICKEN))) {
                        continue;
                    }

                    Chicken chicken = (Chicken) entity;
                    if (!chicken.isAdult()) {
                        continue;
                    }

                    if (ThreadLocalRandom.current().nextInt(10) >= 1) {
                        continue;
                    }

                    player.getWorld().dropItem(entity.getLocation(), new ItemStack(Material.EGG));
                    if (NMSUtils.isAtLeastOneNine()) {
                        player.getWorld().playSound(entity.getLocation(), Sound.valueOf("ENTITY_CHICKEN_EGG"), 2F, 1F);
                    } else {
                        player.getWorld().playSound(entity.getLocation(), Sound.CHICKEN_EGG_POP, 2F, 1F);
                    }
                }
            }
        }, 20 * 30, 20 * 60); // delay, period

    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public int getTotalTasksToFinish() {
        return totalTasksToFinish;
    }

    public void triggerEndGame(Player winner) {
        String newGameMsg = ChatColor.YELLOW + "To begin a new game, type " + ChatColor.AQUA + "/newgame";
        if (gameOver) {
            String gameCompletedMsg = ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + ChatColor.ITALIC + " has completed all tasks!";
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(gameCompletedMsg);
                player.sendMessage(newGameMsg);
            }
            return;
        }

        gameOver = true;

        String gameWonMsg = ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + ChatColor.ITALIC + winner.getName() + ChatColor.GOLD + ChatColor.ITALIC + " has won the game!";

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(gameWonMsg);
            player.sendMessage(newGameMsg);
            if (NMSUtils.isAtLeastOneThirteen()) {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL"), 1, 1);
            } else if (NMSUtils.isAtLeastOneNine()) {
                player.playSound(player.getLocation(), Sound.valueOf("ENTITY_ENDERDRAGON_GROWL"), 1, 1);
            } else {
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
            }

            sendGameOverTitle(player, winner);
        }
    }

    private void sendGameOverTitle(Player player, Player winner) {
        NMSUtils.sendTitleMessage(player, "Game Over", ChatColor.GOLD + "Winner: " + ChatColor.LIGHT_PURPLE + winner.getPlayer().getName());
    }

}
