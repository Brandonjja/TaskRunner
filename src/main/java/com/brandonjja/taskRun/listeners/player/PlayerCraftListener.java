package com.brandonjja.taskRun.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerCraftListener implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item.getType() == Material.GLOWSTONE) {
            event.setCancelled(true);
            event.getViewers().get(0).sendMessage(ChatColor.YELLOW + "You cannot craft Glowstone in TaskRunner!");
        }
    }
}
