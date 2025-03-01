package com.brandonjja.taskRun;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.brandonjja.taskRun.commands.CommandManager;
import com.brandonjja.taskRun.game.Game;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.listeners.ListenerManager;

public class TaskRun extends JavaPlugin {

    public static Game currentGame;

    private static final Map<UUID, PlayerTR> PLAYER_LIST = new HashMap<>();

    private static TaskRun plugin;

    @Override
    public void onEnable() {
        plugin = this;

        PLAYER_LIST.clear();

        for (Player player : Bukkit.getOnlinePlayers()) {
            addPlayer(player);
        }

        CommandManager.registerCommands();
        ListenerManager.registerListeners();
    }

    @Override
    public void onDisable() {
        plugin = null;

        PLAYER_LIST.clear();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     * Adds a player to the game player list, and assigns a scoreboard if necessary
     *
     * @param player the player to add
     */
    public static void addPlayer(Player player) {
        if (!PLAYER_LIST.containsKey(player.getUniqueId())) {
            PlayerTR trPlayer;
            if (currentGame != null) {
                trPlayer = new PlayerTR(player, currentGame.getTaskList());
            } else {
                trPlayer = new PlayerTR(player);
            }
            PLAYER_LIST.put(player.getUniqueId(), trPlayer);
        } else {
            PlayerTR trPlayer = getPlayer(player);
            trPlayer.updatePlayer(player);
            trPlayer.setNewScoreboard(false, false);
        }
    }

    /**
     * Retrieves a player from the player list
     *
     * @param player
     * @return PlayerTR object for the player
     */
    public static PlayerTR getPlayer(Player player) {
        PlayerTR playerTR = PLAYER_LIST.get(player.getUniqueId());
        if (playerTR == null) {
            addPlayer(player);
            playerTR = PLAYER_LIST.get(player.getUniqueId());
        }

        return playerTR;
    }

    /**
     * Removes the Player object from PlayerTR for GC when they're offline
     */
    public static void removePlayer(Player player) {
        PLAYER_LIST.get(player.getUniqueId()).updatePlayer(null);
    }
}
