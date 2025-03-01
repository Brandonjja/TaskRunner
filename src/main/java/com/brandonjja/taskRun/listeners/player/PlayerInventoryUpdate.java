package com.brandonjja.taskRun.listeners.player;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.game.Task;
import com.brandonjja.taskRun.nms.NMSUtils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryUpdate implements Listener {

    private static final String DIAMONDS_HOVER_MESSAGE = ChatColor.GREEN + "DIAMONDS!\n" + ChatColor.ITALIC + "Achievement\n" + ChatColor.WHITE + "Acquire diamonds with your iron tools";
    private static final String BLAZE_ROD_HOVER_MESSAGE = ChatColor.GREEN + "Into Fire\n" + ChatColor.ITALIC + "Achievement\n" + ChatColor.WHITE + "Relieve a Blaze of its rod";

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        PlayerTR trPlayer = TaskRun.getPlayer(event.getPlayer());
        ItemStack item = event.getItem().getItemStack();
        Material itemType = item.getType();

        if (itemType == Material.OBSIDIAN) {
            int ctr = item.getAmount();
            do {
                trPlayer.completeTask(Task.GATHER_OBSIDIAN);
            } while (--ctr > 0);
        } else if (itemType == Material.BLAZE_ROD) {
            handleBlazeRodAchievement(trPlayer);
            int ctr = item.getAmount();
            do {
                trPlayer.completeTask(Task.COLLECT_BLAZE_RODS);
            } while (--ctr > 0);
        } else if (itemType == Material.EMERALD) {
            for (int i = 0; i < item.getAmount(); i++) {
                trPlayer.addEmeraldCollected();
            }
            event.getItem().remove();
            event.setCancelled(true);
        } else if (itemType == Material.DIAMOND) {
            handleDiamondsAchievement(trPlayer);
        }
    }

    /**
     * Used to fix bug where achievements don't always show up, in 1.8.x
     *
     * @param trPlayer the player who completed this achievement
     */
    private void handleBlazeRodAchievement(PlayerTR trPlayer) {
        if (NMSUtils.isAtLeastOneTwelve()) {
            return;
        }

        if (!trPlayer.hasGottenBlazeRod() && !trPlayer.getPlayer().hasAchievement(Achievement.GET_BLAZE_ROD)) {
            trPlayer.pickupBlazeRod();

            TextComponent messageBase = new TextComponent(trPlayer.getPlayer().getName() + " has just earned the achievement ");
            TextComponent achievement = new TextComponent("[Into Fire]");

            achievement.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            achievement.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(BLAZE_ROD_HOVER_MESSAGE).create()));

            messageBase.addExtra(achievement);

            Bukkit.getServer().spigot().broadcast(messageBase);
        }
    }

    /**
     * Used to fix bug where achievements don't always show up, in 1.8.x
     *
     * @param trPlayer the player who completed this achievement
     */
    private void handleDiamondsAchievement(PlayerTR trPlayer) {
        if (NMSUtils.isAtLeastOneTwelve()) {
            return;
        }

        if (!trPlayer.hasDiamonds() && !trPlayer.getPlayer().hasAchievement(Achievement.GET_DIAMONDS)) {
            trPlayer.pickupDiamonds();

            TextComponent messageBase = new TextComponent(trPlayer.getPlayer().getName() + " has just earned the achievement ");
            TextComponent achievement = new TextComponent("[DIAMONDS!]");

            achievement.setColor(net.md_5.bungee.api.ChatColor.GREEN);
            achievement.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(DIAMONDS_HOVER_MESSAGE).create()));

            messageBase.addExtra(achievement);

            Bukkit.getServer().spigot().broadcast(messageBase);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        PlayerTR trPlayer = TaskRun.getPlayer(event.getPlayer());
        ItemStack item = event.getItemDrop().getItemStack();
        Material itemType = item.getType();

        if (itemType == Material.OBSIDIAN) {
            trPlayer.removeTaskProgress(Task.GATHER_OBSIDIAN, item.getAmount());
        } else if (itemType == Material.BLAZE_ROD) {
            trPlayer.removeTaskProgress(Task.COLLECT_BLAZE_RODS, item.getAmount());
        }
    }

}
