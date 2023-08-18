package com.brandonjja.taskRun.listeners.player;

import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Achievement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class PlayerAchievementListener implements Listener {

    @EventHandler
    public void onAchievement(PlayerAchievementAwardedEvent event) {
        if (NMSUtils.isAtLeastOneTwelve()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerTR trPlayer = TaskRun.getPlayer(player);
        Achievement achievement = event.getAchievement();

        if (achievement == Achievement.NETHER_PORTAL) {
            if (trPlayer.hasEnteredNether()) {
                event.setCancelled(true);
            }
        } else if (achievement == Achievement.GET_BLAZE_ROD) {
            if (trPlayer.hasGottenBlazeRod()) {
                event.setCancelled(true);
            }
        } else if (achievement == Achievement.GET_DIAMONDS) {
            if (trPlayer.hasDiamonds()) {
                event.setCancelled(true);
            }
        }
    }
}
