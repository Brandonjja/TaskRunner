package com.brandonjja.taskRun.commands.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.brandonjja.taskRun.commands.TaskRunCommand;

public class PauseCommand extends TaskRunCommand implements Listener {
	
	private static boolean frozen = false;

	@Override
	public boolean execute(Player player, String[] args) {
		if (frozen) {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(ChatColor.BLUE + "The game has been " + ChatColor.GREEN + ChatColor.BOLD + "unpaused" + ChatColor.BLUE + " by " + player.getName() + ", so you are now " + ChatColor.AQUA + ChatColor.BOLD + "unfrozen!");
				for (Entity entity : pl.getNearbyEntities(40, 40, 40)) {
					if (entity instanceof LivingEntity) {
						if (entity instanceof Player) {
							continue;
						}
						if (((LivingEntity) entity).hasPotionEffect(PotionEffectType.SLOW)) {
							((LivingEntity) entity).removePotionEffect(PotionEffectType.SLOW);
						}
					}
				}
			}
		} else {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.sendMessage(ChatColor.BLUE + "The game has been " + ChatColor.RED + ChatColor.BOLD + "paused" + ChatColor.BLUE + " by " + player.getName() + ", so you are now " + ChatColor.AQUA + ChatColor.BOLD + "frozen!");
				for (Entity entity : pl.getNearbyEntities(40, 40, 40)) {
					if (entity instanceof LivingEntity) {
						if (entity instanceof Player) {
							continue;
						}
						((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 20));
					}
				}
			}
		}
		
		toggleGameRule(player);
		
		frozen = !frozen;
		return true;
	}
	
	private void toggleGameRule(Player player) {
		if (frozen) {
			player.getWorld().setGameRuleValue("doDaylightCycle", "true");
		} else {
			player.getWorld().setGameRuleValue("doDaylightCycle", "false");
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onMove(PlayerMoveEvent e) {
		if (frozen) {
			e.getPlayer().teleport(e.getFrom());
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onDamage(EntityDamageEvent e) {
		if (frozen && e.getEntity() instanceof Player) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onHunger(FoodLevelChangeEvent e) {
		if (frozen) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void onEat(PlayerItemConsumeEvent e) {
		if (frozen) {
			e.setCancelled(true);
		}
	}
}
