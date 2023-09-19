package com.brandonjja.taskRun.game;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.nms.NMSUtils;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerTR {

    private Player player;
    private TObjectIntMap<Task> taskList;
    private int totalTasksCompleted;
    private ScoreboardTR board = null;
    private boolean hasScoreboard;
    private boolean enteredNether;
    private boolean gotBlazeRods;
    private boolean gotDiamonds;
    private Location location;
    private int emeraldsCollected;

    public PlayerTR(Player player, List<Task> taskList) {
        this.player = player;
        this.totalTasksCompleted = 0;
        this.hasScoreboard = true;
        this.enteredNether = false;
        this.gotBlazeRods = false;
        this.emeraldsCollected = 0;

        if (taskList != null) {
            setTaskList(taskList);
        }
    }

    public PlayerTR(Player player) {
        this(player, null);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Updates the player object, used when a player relogs
     *
     * @param player
     */
    public void updatePlayer(Player player) {
        this.player = player;
    }

    /**
     * Creates a list of given tasks to complete for a game
     *
     * @param taskList
     */
    public void setTaskList(List<Task> taskList) {
        this.taskList = new TObjectIntHashMap<>();
        for (Task task : taskList) {
            this.taskList.put(task, 0);
        }

        resetTasksCompleted();
    }

    /**
     * Takes the list of tasks for a current game and returns a formatted
     * String displaying the player's current progress
     *
     * @return a formatted String of current tasks
     */
    public String getTaskListString() {
        if (taskList == null) {
            player.sendMessage(ChatColor.RED + "Error");
            return "null";
        }
        StringBuilder message = new StringBuilder(ChatColor.GOLD + "---------- Tasks ----------\n");
        int ctr = 0;
        for (Task task : taskList.keySet()) {
            if (hasCompletedTask(task)) {
                message.append(ChatColor.RED);
            } else {
                message.append(ChatColor.YELLOW);
            }

            message.append(++ctr)
                    .append(". ")
                    .append(task.toString(taskList.get(task)))
                    .append("\n");
        }
        return message.toString();
    }

    public boolean hasCompletedTask(Task task) {
        return taskList.get(task) >= task.getNeededCompletions();
    }

    /**
     * Increase the total number of tasks completed by 1
     */
    public void finishTask() {
        totalTasksCompleted++;
    }

    /**
     * Check to see if the player completed all their tasks, to win the game
     */
    public void checkEndGame() {
        if (totalTasksCompleted == TaskRun.currentGame.getTotalTasksToFinish()) {
            TaskRun.currentGame.triggerEndGame(player);
        }
    }

    /**
     * Returns the total number of tasks completed
     */
    public int getFinishedTasks() {
        return totalTasksCompleted;
    }

    /**
     * Resets the total number of tasks completed to zero
     */
    private void resetTasksCompleted() {
        totalTasksCompleted = 0;
    }

    /**
     * Returns the player's list of tasks
     */
    public TObjectIntMap<Task> getTaskList() {
        return taskList;
    }

    /**
     * Increases the completion of a task by 1
     */
    public void completeTask(Task task) {
        Game game = TaskRun.currentGame;
        if (game == null) {
            return;
        }

        if (!game.getTaskList().contains(task)) {
            return;
        }

        if (hasCompletedTask(task)) {
            return;
        }

        int totalCompleted = taskList.adjustOrPutValue(task, 1, 1);
        board.updateTask(task, player, totalCompleted - 1, totalCompleted);
        if (totalCompleted >= task.getNeededCompletions()) {
            task.announceTaskComplete(player);
        }
    }

    /**
     * Decreases the completion of a task by the specified amount
     *
     * @param task    the Task to remove progress from
     * @param howMuch the amount to decrease by
     */
    public void removeTaskProgress(Task task, int howMuch) {
        Game game = TaskRun.currentGame;
        if (game == null) {
            return;
        }

        if (!game.getTaskList().contains(task)) {
            return;
        }

        if (hasCompletedTask(task)) {
            return;
        }

        int currentlyCompleted = taskList.get(task);
        taskList.put(task, Math.max(currentlyCompleted - howMuch, 0));
        board.updateTask(task, player, currentlyCompleted, taskList.get(task));
    }

    /**
     * Sets the Scoreboard for the player. Used to assign a new Scoreboard, and to toggle the Scoreboard on/off
     *
     * @param newGame set true if there is not a game running, otherwise set false
     * @param toggle  set true if the Scoreboard is being toggled through a command, otherwise set false
     */
    public void setNewScoreboard(boolean newGame, boolean toggle) {
        if (!newGame && board != null && toggle) {
            if (hasScoreboard) {
                player.setScoreboard(board.getScoreboard());
            } else {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            return;
        }
        this.board = new ScoreboardTR(this);
    }

    public ScoreboardTR getBoard() {
        return board;
    }

    public boolean getHasScoreboard() {
        return hasScoreboard;
    }

    public void toggleScoreboard() {
        hasScoreboard = !hasScoreboard;
    }

    /**
     * true if the player has ever entered the Nether
     */
    public boolean hasEnteredNether() {
        return enteredNether;
    }

    /**
     * set to true when the player enters the Nether for the first time
     */
    public void enterNether() {
        enteredNether = true;
    }

    /**
     * true if the player has ever picked up a Blaze Rod
     */
    public boolean hasGottenBlazeRod() {
        return gotBlazeRods;
    }

    /**
     * set to true when the player picks up their first Blaze Rod
     */
    public void pickupBlazeRod() {
        gotBlazeRods = true;
    }

    /**
     * true if the player has ever picked up diamonds
     */
    public boolean hasDiamonds() {
        return gotDiamonds;
    }

    /**
     * set to true when the player picks up their first diamond
     */
    public void pickupDiamonds() {
        gotDiamonds = true;
    }

    public void saveLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void addEmeraldCollected() {
        emeraldsCollected++;

        if (emeraldsCollected == 3) {
            String msg = ChatColor.AQUA + player.getName() + ChatColor.GREEN + " has collected 3 emeralds and was rewarded a diamond!";
            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.sendMessage(msg);

                if (NMSUtils.isAtLeastOneNine()) {
                    pl.playSound(pl.getLocation(), Sound.valueOf("ENTITY_PLAYER_LEVELUP"), 1, 1);
                } else {
                    pl.playSound(pl.getLocation(), Sound.LEVEL_UP, 1, 1);
                }
            }
            if (player.getInventory().firstEmpty() == -1 && !player.getInventory().contains(Material.DIAMOND)) {
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.DIAMOND));
                player.sendMessage(ChatColor.RED + "Your inventory was full, so your diamond was dropped on the ground");
            } else {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            }
            emeraldsCollected = 0;
        } else {
            player.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "You have collected: " + emeraldsCollected + " emerald(s).. maybe you should find more!");
        }

    }
}
