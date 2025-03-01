package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(itemDrop -> {
            Material material = itemDrop.getType();
            return material == Material.BLAZE_ROD || material == Material.OBSIDIAN || material == Material.COMPASS;
        });
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
    }
}
