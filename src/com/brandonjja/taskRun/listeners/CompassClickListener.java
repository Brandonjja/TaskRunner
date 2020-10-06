package com.brandonjja.taskRun.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassClickListener implements Listener {
	private Player track(Player p) {
		double minDistanceFound = Double.POSITIVE_INFINITY;
		Player target = null;
		
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.equals(p)) {
				continue;
			}
			double distanceTo;
			try {
				distanceTo = p.getLocation().distance(pl.getLocation());
			} catch (IllegalArgumentException ex) {
				distanceTo = Double.POSITIVE_INFINITY;
			}
			if (distanceTo > minDistanceFound) {
				continue;
			}
			minDistanceFound = distanceTo;
			target = pl;
		}
		if (target == null || minDistanceFound == Double.POSITIVE_INFINITY) {
			return null;
		} else {
			return target;
		}
	}

	@EventHandler
	public void onTrack(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.getItemInHand().getType() == Material.COMPASS) {
			Player target = track(player);
			if (target != null) {
				Location loc = target.getLocation();
				player.setCompassTarget(loc);
				player.sendMessage(ChatColor.GREEN + "Currently Tracking: " + ChatColor.AQUA + target.getName());
			} else {
				player.sendMessage(ChatColor.RED + "No players found!");
			}
		}
	}
}
