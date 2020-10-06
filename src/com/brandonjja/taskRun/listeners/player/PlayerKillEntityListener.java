package com.brandonjja.taskRun.listeners.player;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.brandonjja.taskRun.game.Game;

public class PlayerKillEntityListener implements Listener {
	
	@EventHandler
	public void onPigmanKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof PigZombie && e.getEntity().getKiller() instanceof Player) {
			Game.completeTask(e.getEntity().getKiller(), 4);
		}
	}
	
	@EventHandler
	public void onCreeperKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof Creeper && e.getEntity().getKiller() instanceof Player) {
			Game.completeTask(e.getEntity().getKiller(), 10);
		}
	}
	
	@EventHandler
	public void onGhastKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof Ghast && e.getEntity().getKiller() instanceof Player) {
			Game.completeTask(e.getEntity().getKiller(), 14);
		}
	}
}
