package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Iterator<ItemStack> it = event.getDrops().iterator();
        while (it.hasNext()) {
            ItemStack itemStack = it.next();
            Material material = itemStack.getType();
            if (material == Material.BLAZE_ROD) {
                it.remove();
                continue;
            }

            if (material == Material.OBSIDIAN) {
                it.remove();
                continue;
            }

            if (material == Material.COMPASS) {
                it.remove();
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
    }
}
