package com.brandonjja.taskRun.listeners.tasks;

import java.util.List;

import com.brandonjja.taskRun.game.Task;
import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.*;
import org.bukkit.entity.Skeleton.SkeletonType;
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
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.game.TR_Task;

public class TaskListeners implements Listener {

    // This class includes the Listeners (triggers) for all TaskRunner Tasks

    // Travel to the Nether
    @EventHandler
    public void onTravelToNether(PlayerPortalEvent event) {
        if (event.getTo().getWorld().getEnvironment() == Environment.NETHER) {
            PlayerTR trPlayer = TaskRun.getPlayer(event.getPlayer());
            trPlayer.completeTask(Task.TRAVEL_TO_NETHER);
        }
    }

    // Grow a Tree with Bonemeal
    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        if (event.getBlocks().size() > 1) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.GROW_TREE_BONEMEAL);
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
        } else if ((entityKilled instanceof Skeleton && ((Skeleton) entityKilled).getSkeletonType() == SkeletonType.WITHER)
                || (NMSUtils.isAtLeastOneTwelve() && entityKilled.getType() == EntityType.valueOf("WITHER_SKELETON"))) {
            trPlayer.completeTask(29);
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
    public void onTNTLight(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        PlayerTR trPlayer = TaskRun.getPlayer(player);
        if (player.getItemInHand() != null && player.getItemInHand().getType() == Material.FLINT_AND_STEEL && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.TNT && !player.isSneaking()) {
                trPlayer.completeTask(25);
            }
        }
    }

    @EventHandler
    public void onXP(PlayerLevelChangeEvent e) {
        if (e.getNewLevel() >= 15) {
            TaskRun.getPlayer(e.getPlayer()).completeTask(26);
        }
    }

    @EventHandler
    public void onStandOnBlock(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        PlayerTR trPlayer = TaskRun.getPlayer(player);
        Location playerLocation = player.getLocation();
        Location locationSub1 = new Location(playerLocation.getWorld(), playerLocation.getX(), playerLocation.getY() - 1, playerLocation.getZ());
        if (locationSub1.getBlock().getType() == Material.BEDROCK) {
            trPlayer.completeTask(8);
        } else if (locationSub1.getBlock().getType() == Material.DIAMOND_BLOCK) {
            trPlayer.completeTask(13);
        }
        if (playerLocation.getWorld().getEnvironment() == Environment.NETHER && playerLocation.getBlockY() == 1) {
            trPlayer.completeTask(28);
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
        List<TR_Task> taskList = trPlayer.getTaskList();
        if (taskList == null) {
            return;
        }
        for (TR_Task task : taskList) {
            if (task.getTaskID() == 3 || task.getTaskID() == 12) {
                task.removeTaskProgress(trPlayer, task.getTaskID(), Integer.MAX_VALUE);
            }
        }

        if (e.getDeathMessage().contains("lava")) {
            trPlayer.completeTask(17);
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent e) {
        PlayerTR trPlayer = TaskRun.getPlayer(e.getEnchanter());
        if (e.getItem().getType() == Material.GOLD_SPADE) {
            trPlayer.completeTask(19);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        PlayerTR trPlayer = TaskRun.getPlayer((Player) event.getWhoClicked());
        ItemStack item = event.getCurrentItem();
        if (item.getType().getId() == 355 || NMSUtils.isAtLeastOneTwelve() && item.toString().contains("_BED")) {
            trPlayer.completeTask(Task.MAKE_A_BED);
        } else if (item.getType() == Material.CAKE || NMSUtils.isAtLeastOneTwelve() && item.toString().contains("CAKE")) {
            trPlayer.completeTask(21);
        } else if (item.getType() == Material.GOLDEN_APPLE) {
            trPlayer.completeTask(23);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEatChicken(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.RAW_CHICKEN) {
            TaskRun.getPlayer(event.getPlayer()).completeTask(22);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEatStew(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MUSHROOM_SOUP) {
            TaskRun.getPlayer(event.getPlayer()).completeTask(27);
        }
    }
}
