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

	@EventHandler
	public void onTrackPlayer(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getItemInHand().getType() != Material.COMPASS) {
			return;
		}

		Player target = getClosestPlayer(player);
		if (target != null) {
			Location targetLocation = target.getLocation();
			player.setCompassTarget(targetLocation);
			player.sendMessage(ChatColor.GREEN + "Currently Tracking: " + ChatColor.AQUA + target.getName());
			return;
		}

		player.sendMessage(ChatColor.RED + "No players found!");
	}

	/**
	 * Searches for the nearest online player to the tracker.
	 *
	 * @param player the player doing the tracking
	 * @return the nearest player if any exists, otherwise null
	 */
	private Player getClosestPlayer(Player player) {
		double minDistanceFound = Double.POSITIVE_INFINITY;
		Player target = null;

		Location trackerLocation = player.getLocation();
		for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
			if (otherPlayer.equals(player)) {
				continue;
			}

			double distanceToTarget;
			try {
				distanceToTarget = trackerLocation.distanceSquared(otherPlayer.getLocation());
			} catch (IllegalArgumentException ex) {
				distanceToTarget = Double.POSITIVE_INFINITY;
			}

			if (distanceToTarget > minDistanceFound) {
				continue;
			}

			minDistanceFound = distanceToTarget;
			target = otherPlayer;
		}

		return (target == null || minDistanceFound == Double.POSITIVE_INFINITY) ? null : target;
	}
}
