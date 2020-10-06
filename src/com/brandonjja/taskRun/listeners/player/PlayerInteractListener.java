package com.brandonjja.taskRun.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onMilkCow(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		if (player.getItemInHand().getType() == Material.BUCKET) {
			if (e.getRightClicked() instanceof Cow) {
				Game.completeTask(player, 6);
			}
		}
	}
	
	@EventHandler
	public void onThrowSnowball(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (player.getItemInHand().getType() == Material.SNOW_BALL) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Game.completeTask(player, 7);
			}
		}
	}
}
