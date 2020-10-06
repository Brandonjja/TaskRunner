package com.brandonjja.taskRun.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerCraftItemListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if (e.getCurrentItem().getType().getId() == 355) {
			
			Game.completeTask((Player) e.getViewers().get(0), 0); // id 0 = Craft a bed
			
			/*if (TaskRun.currentGame == null) {
				return;
			}
			if (TaskRun.currentGame.idListContains(0)) {
				PlayerTR trPlayer = TaskRun.getPlayer((Player) e.getViewers().get(0));
				trPlayer.completeTask(0); // 0 = Craft a bed
			}*/
		}
	}
}
