package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        for (ItemStack stack : event.getDrops()) {
            Material material = stack.getType();
            if (material == Material.BLAZE_ROD) {
                event.getDrops().remove(stack);
                continue;
            }

            if (material == Material.OBSIDIAN) {
                event.getDrops().remove(stack);
                continue;
            }

            if (material == Material.COMPASS) {
                event.getDrops().remove(stack);
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
    }
}
