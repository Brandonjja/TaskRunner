package com.brandonjja.taskRun.listeners.world;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Tree;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

public class BlockListener implements Listener {
	
	private static Random random = new Random();
	
	@SuppressWarnings("deprecation") // Magic values
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (e.getBlock().getType() == Material.GLOWSTONE) {
			trPlayer.completeTask(16);
		} else if (e.getBlock().getType() == Material.QUARTZ_ORE) {
			trPlayer.completeTask(18);
		} else if (e.getBlock().getType() == Material.NETHERRACK) {
			trPlayer.completeTask(24);
		} else if (e.getBlock().getType() == Material.GLASS) {
			trPlayer.completeTask(30);
		} else if (e.getBlock().getType() == Material.LONG_GRASS || e.getBlock().getType() == Material.DOUBLE_PLANT) {
			if (e.getBlock().getType() == Material.DOUBLE_PLANT) {
				if (e.getBlock().getData() == (byte) 2) {
					trPlayer.completeTask(31);
				} else if (e.getBlock().getData() == (byte) 10) {
					if (e.getBlock().getRelative(BlockFace.DOWN).getData() == (byte) 2) {
						trPlayer.completeTask(31);
					}
				}
			} else {
				trPlayer.completeTask(31);
			}
		}
	}

	@EventHandler
	public void onLeafBreak(BlockBreakEvent e) {
		Block block = e.getBlock();
		if (block.getType() == Material.LEAVES) {
			Tree leaf = (Tree) block.getState().getData();
			if (leaf.getSpecies() == TreeSpecies.GENERIC) {
				if (random.nextInt(100) < 5) {
					block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
				}
			}
		}
	}
}
