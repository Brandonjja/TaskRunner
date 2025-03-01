package com.brandonjja.taskRun.listeners.world;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.game.Task;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Leaves;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Tree;

import java.util.concurrent.ThreadLocalRandom;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        PlayerTR trPlayer = TaskRun.getPlayer(event.getPlayer());
        if (blockType == Material.GLOWSTONE) {
            trPlayer.completeTask(Task.BREAK_GLOWSTONE);
        } else if (blockType == Material.QUARTZ_ORE) {
            trPlayer.completeTask(Task.MINE_QUARTZ_ORE);
        } else if (blockType == Material.NETHERRACK) {
            trPlayer.completeTask(Task.BREAK_NETHERRACK);
        } else if (blockType == Material.GLASS) {
            trPlayer.completeTask(Task.BREAK_GLASS);
        } else if (blockType == Material.LONG_GRASS || blockType == Material.DOUBLE_PLANT) {
            if (blockType == Material.DOUBLE_PLANT) {
                if (event.getBlock().getData() == (byte) 2) {
                    trPlayer.completeTask(Task.TOUCH_GRASS);
                } else if (event.getBlock().getData() == (byte) 10) {
                    if (event.getBlock().getRelative(BlockFace.DOWN).getData() == (byte) 2) {
                        trPlayer.completeTask(Task.TOUCH_GRASS);
                    }
                }
            } else {
                trPlayer.completeTask(Task.TOUCH_GRASS);
            }
        }
    }

    @EventHandler
    public void onLeafBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.LEAVES) {
            return;
        }

        MaterialData leaf = block.getState().getData();
        boolean shouldTryDrop = false;
        if (leaf instanceof Leaves && ((Leaves) leaf).getSpecies() == TreeSpecies.GENERIC) {
            shouldTryDrop = true;
        } else if (leaf instanceof Tree && ((Tree) leaf).getSpecies() == TreeSpecies.GENERIC) {
            shouldTryDrop = true;
        }

        if (shouldTryDrop && ThreadLocalRandom.current().nextInt(100) < 5) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
        }
    }
}
