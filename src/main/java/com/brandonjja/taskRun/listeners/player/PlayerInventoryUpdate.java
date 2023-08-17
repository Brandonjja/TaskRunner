package com.brandonjja.taskRun.listeners.player;

import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerInventoryUpdate implements Listener {
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		ItemStack item = e.getItem().getItemStack();
		Material itemType = item.getType();
		
		if (itemType == Material.OBSIDIAN) {
			int ctr = item.getAmount();
			do {
				trPlayer.completeTask(3);
			} while (--ctr > 0);
		} else if (itemType == Material.BLAZE_ROD) {
			handleBlazeRodAchievement(trPlayer);
			int ctr = item.getAmount();
			do {
				trPlayer.completeTask(12);
			} while (--ctr > 0);
		} else if (itemType == Material.EMERALD) {
			for (int i = 0; i < item.getAmount(); i++) {
				trPlayer.addEmeraldCollected();
			}
			e.getItem().remove();
			e.setCancelled(true);
		} else if (itemType == Material.DIAMOND) {
			handleDiamondsAchievement(trPlayer);
		}
	}
	
	final static String diamondsHoverMessage = ChatColor.GREEN + "DIAMONDS!\n" + ChatColor.ITALIC + "Achievement\n" + ChatColor.WHITE + "Acquire diamonds with your iron tools";
	final static String blazeRodHoverMessage = ChatColor.GREEN + "Into Fire\n" + ChatColor.ITALIC + "Achievement\n" + ChatColor.WHITE + "Relieve a Blaze of its rod";
	
	/** Used to fix bug where achievements don't always show up, in 1.8.x */
	private void handleBlazeRodAchievement(PlayerTR trPlayer) {
		if (NMSUtils.isAtLeastOneTwelve()) {
			return;
		}
		if (!trPlayer.hasGottenBlazeRod() && !trPlayer.getPlayer().hasAchievement(Achievement.GET_BLAZE_ROD)) {
			trPlayer.pickupBlazeRod();
			StringBuilder sb = new StringBuilder(trPlayer.getPlayer().getName())
					.append(" has just earned the achievement ");
			
			TextComponent messageBase = new TextComponent(sb.toString());
			TextComponent achievement = new TextComponent("[Into Fire]");
			
			achievement.setColor(net.md_5.bungee.api.ChatColor.GREEN);
			achievement.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(blazeRodHoverMessage).create()));
			
			messageBase.addExtra(achievement);
			
			Bukkit.getServer().spigot().broadcast(messageBase);
		}
	}
	
	/** Used to fix bug where achievements don't always show up, in 1.8.x */
	private void handleDiamondsAchievement(PlayerTR trPlayer) {
		if (NMSUtils.isAtLeastOneTwelve()) {
			return;
		}
		if (!trPlayer.hasDiamonds() && !trPlayer.getPlayer().hasAchievement(Achievement.GET_DIAMONDS)) {
			trPlayer.pickupDiamonds();
			StringBuilder sb = new StringBuilder(trPlayer.getPlayer().getName())
					.append(" has just earned the achievement ");
			
			TextComponent messageBase = new TextComponent(sb.toString());
			TextComponent achievement = new TextComponent("[DIAMONDS!]");
			
			achievement.setColor(net.md_5.bungee.api.ChatColor.GREEN);
			achievement.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(diamondsHoverMessage).create()));
			
			messageBase.addExtra(achievement);
			
			Bukkit.getServer().spigot().broadcast(messageBase);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		ItemStack item = e.getItemDrop().getItemStack();
		Material itemType = item.getType();
		
		if (itemType == Material.OBSIDIAN) {
			trPlayer.removeTaskProgress(3, item.getAmount());
		} else if (itemType == Material.BLAZE_ROD) {
			trPlayer.removeTaskProgress(12, item.getAmount());
		}
	}
	
	@EventHandler
	public void onChestItem(InventoryCloseEvent e) {
		/*if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}*/
		//Player player = (Player) e.getPlayer();
	}

}
