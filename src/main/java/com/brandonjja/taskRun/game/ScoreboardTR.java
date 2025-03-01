package com.brandonjja.taskRun.game;

import gnu.trove.iterator.TObjectIntIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.brandonjja.taskRun.TaskRun;

public class ScoreboardTR {

    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardTR(PlayerTR player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("taskRun", "dummy");
        objective.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Task Board");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (player.getHasScoreboard()) {
            player.getPlayer().setScoreboard(scoreboard);
        } else {
            player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        setTasks(player);
    }

    /**
     * Prints the list of tasks on the Task Board for the first time
     *
     * @param player PlayerTR object of the player who is receiving the scoreboard
     */
    private void setTasks(PlayerTR player) {
        Game game = TaskRun.currentGame;
        if (game == null) {
            return;
        }

        int position = game.getTotalTasksToFinish();
        for (TObjectIntIterator<Task> it = player.getTaskList().iterator(); it.hasNext(); ) {
            it.advance();
            objective.getScore(it.key().toString(it.value())).setScore(position--);
        }

        position = game.getTotalTasksToFinish() + 2;

        objective.getScore(" ").setScore(position - 1);

        String playerScore;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            int finishedTasks = TaskRun.getPlayer(onlinePlayer).getFinishedTasks();
            int totalTasks = game.getTotalTasksToFinish();
            playerScore = ChatColor.AQUA + onlinePlayer.getName() + " " + ChatColor.YELLOW + finishedTasks + "/" + totalTasks;
            objective.getScore(playerScore).setScore(position++);
        }
    }


    /**
     * Updates the progress of a task on the Task Board. Increments the score
     * shown to all players if the task gets completed
     *
     * @param task     the task to update the progress for
     * @param player   the player to update the score for
     * @param oldScore the old progress of the task
     * @param newScore the new progress of the task
     */
    public void updateTask(Task task, Player player, int oldScore, int newScore) {
        Score pos = objective.getScore(task.toString(oldScore));
        int position = pos.getScore();
        scoreboard.resetScores(task.toString(oldScore));
        objective.getScore(task.toString(newScore)).setScore(position);
        if (task.getNeededCompletions() <= newScore) {
            pos = objective.getScore(ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + TaskRun.currentGame.getTotalTasksToFinish());
            position = pos.getScore();
            updateScoreAllPlayers(player, position);
        }
    }

    /**
     * Updates a single player's score shown to all players, when a task gets marked as finished
     *
     * @param player   the player who finished a task
     * @param position the position on the scoreboard to update
     */
    private void updateScoreAllPlayers(Player player, int position) {
        Game game = TaskRun.currentGame;
        String resetScore = ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks()) + "/" + game.getTotalTasksToFinish();
        String newScore = (ChatColor.AQUA + player.getName() + " " + ChatColor.YELLOW + (TaskRun.getPlayer(player).getFinishedTasks() + 1) + "/" + game.getTotalTasksToFinish());
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            PlayerTR trPlayer = TaskRun.getPlayer(onlinePlayer);
            ScoreboardTR scoreboard = trPlayer.getBoard();
            if (scoreboard == null) {
                continue;
            }

            scoreboard.getScoreboard().resetScores(resetScore);
            scoreboard.getObjective().getScore(newScore).setScore(position);
        }
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    private Objective getObjective() {
        return objective;
    }
}
