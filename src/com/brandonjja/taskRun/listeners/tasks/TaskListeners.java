package com.brandonjja.taskRun.listeners.tasks;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.tasks.TR_Task;

public class TaskListeners implements Listener {
	
	// This class includes the Listeners (triggers) for all TaskRunner Tasks
	
	// Travel to the Nether
	@EventHandler
	public void onTravelToNether(PlayerPortalEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		if (e.getTo().getWorld().getEnvironment() == Environment.NETHER) {
			trPlayer.completeTask(1);
		}
	}
	
	// Grow a Tree with Bonemeal
	@EventHandler
	public void onTreeGrow(StructureGrowEvent e) {
		if (e.getPlayer() != null) {
			PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
			if (e.getBlocks().size() > 1) {
				trPlayer.completeTask(2);
			}
		}
	}
	
	// Kill 5 Pigmen
	// Kill 15 Creepers
	// Kill a Ghast
	@EventHandler
	public void onEntityKill(EntityDeathEvent e) {
		
		if (!(e.getEntity().getKiller() instanceof Player)) {
			return;
		}
		
		PlayerTR trPlayer = TaskRun.getPlayer(e.getEntity().getKiller());
		LivingEntity entityKilled = e.getEntity();
		
		if (entityKilled instanceof PigZombie) {
			trPlayer.completeTask(4);
		} else if (entityKilled instanceof Creeper) {
			trPlayer.completeTask(10);
		} else if (entityKilled instanceof Ghast) {
			trPlayer.completeTask(14);
		}
	}
	
	// Catch 2 Fish
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFishCatch(PlayerFishEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getPlayer());
		if (e.getState() == State.CAUGHT_FISH) {
			Item item = (Item) e.getCaught();
			if (item.getItemStack().getType().getId() == 349) {
				trPlayer.completeTask(5);
			}
		}
	}
	
	@EventHandler
	public void onMilkCow(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getItemInHand().getType() == Material.BUCKET) {
			if (e.getRightClicked() instanceof Cow) {
				trPlayer.completeTask(6);
			}
		}
	}
	
	@EventHandler
	public void onThrowSnowball(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getItemInHand().getType() == Material.SNOW_BALL) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				trPlayer.completeTask(7);
			}
		}
	}
	
	@EventHandler
	public void onStandOnBlock(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.BEDROCK) {
			trPlayer.completeTask(8);
		} else if (player.getLocation().add(0, -1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
			trPlayer.completeTask(13);
		}
	}
	
	@EventHandler
	public void onBuildLimitReached(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getLocation().getY() > 256.5) {
			trPlayer.completeTask(9);
		}
	}
	
	@EventHandler
	public void onRunXBlocks(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getLocation().getX() > 512) {
			trPlayer.completeTask(20);
		}
	}
	
	@EventHandler
	public void onShearSheep(PlayerInteractAtEntityEvent e) {
		Player player = e.getPlayer();
		PlayerTR trPlayer = TaskRun.getPlayer(player);
		if (player.getItemInHand().getType() == Material.SHEARS) {
			if (e.getRightClicked() instanceof Sheep) {
				Sheep sheep = (Sheep) e.getRightClicked();
				if (sheep.isAdult() && !sheep.isSheared()) {
					trPlayer.completeTask(15);
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getEntity());
		for (TR_Task task : trPlayer.getTaskList()) {
			if (task.getTaskID() == 3 || task.getTaskID() == 12) {
				task.removeTaskProgress(trPlayer, task.getTaskID(), Integer.MAX_VALUE);
			}
		}
		
		if (e.getDeathMessage().contains("lava")) {
			trPlayer.completeTask(17);
		}
	}
	
	@EventHandler
	public void onEnchantHoe(EnchantItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer(e.getEnchanter());
		if (e.getItem().getType() == Material.GOLD_SPADE) {
			trPlayer.completeTask(19);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		PlayerTR trPlayer = TaskRun.getPlayer((Player) e.getWhoClicked());
		ItemStack item = e.getCurrentItem();
		if (item.getType().getId() == 355) {
			
			trPlayer.completeTask(0); // id 0 = Craft a bed
			
		} else if (item.getType() == Material.CAKE) {
			trPlayer.completeTask(21);
		} else if (item.getType() == Material.GOLDEN_APPLE) {
			trPlayer.completeTask(23);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onEatChicken(PlayerItemConsumeEvent e) {
		if (e.getItem().getType() == Material.RAW_CHICKEN) {
			if (e.isCancelled()) {
				return;
			}
			TaskRun.getPlayer(e.getPlayer()).completeTask(22);
		}
	}
}
