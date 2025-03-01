package com.brandonjja.taskRun.commands.handler;

import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.commands.TaskRunCommand;
import com.brandonjja.taskRun.game.Game;

public class NewGameCommand extends TaskRunCommand {

    @Override
    public boolean execute(Player player, String[] args) {
        if (!player.hasPermission("taskrunner.newgame")) {
            return true;
        }

        TaskRun.currentGame = new Game();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            TaskRun.getPlayer(onlinePlayer).setNewScoreboard(true, false);
            if (NMSUtils.isAtLeastOneNine()) {
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.valueOf("ENTITY_WITHER_SPAWN"), 1F, 1F);
            } else {
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.WITHER_SPAWN, 1F, 1F);
            }
        }

        return true;
    }
}
