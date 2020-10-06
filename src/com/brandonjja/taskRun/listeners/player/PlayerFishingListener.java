package com.brandonjja.taskRun.listeners.player;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

import com.brandonjja.taskRun.game.Game;

public class PlayerFishingListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFishCatch(PlayerFishEvent e) {
		if (e.getState() == State.CAUGHT_FISH) {
			Item item = (Item) e.getCaught();
			if (item.getItemStack().getType().getId() == 349) {
				Game.completeTask(e.getPlayer(), 5);
			}
		}
	}
}
