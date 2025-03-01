package com.brandonjja.taskRun.listeners.tasks;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.game.PlayerTR;
import com.brandonjja.taskRun.game.Task;
import com.brandonjja.taskRun.nms.NMSUtils;
import gnu.trove.map.TObjectIntMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
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

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {
        LivingEntity entityKilled = event.getEntity();
        Player killer = entityKilled.getKiller();
        if (killer == null) {
            return;
        }

        PlayerTR trPlayer = TaskRun.getPlayer(killer);

        if (entityKilled instanceof PigZombie) {
            trPlayer.completeTask(Task.KILL_PIGMEN);
        } else if (entityKilled instanceof Creeper) {
            trPlayer.completeTask(Task.KILL_CREEPERS);
        } else if (entityKilled instanceof Ghast) {
            trPlayer.completeTask(Task.KILL_GHAST);
        } else if ((entityKilled instanceof Skeleton && ((Skeleton) entityKilled).getSkeletonType() == SkeletonType.WITHER)
                || (NMSUtils.isAtLeastOneTwelve() && entityKilled.getType() == EntityType.valueOf("WITHER_SKELETON"))) {
            trPlayer.completeTask(Task.KILL_WITHER_SKELETON);
        }
    }

    // Catch 2 Fish
    @EventHandler
    public void onFishCatch(PlayerFishEvent event) {
        if (event.getState() != State.CAUGHT_FISH) {
            return;
        }

        Entity caught = event.getCaught();
        if (!(caught instanceof Item)) {
            return;
        }

        Item itemCaught = (Item) caught;
        if (itemCaught.getItemStack().getType() != Material.RAW_FISH) {
            return;
        }

        PlayerTR trPlayer = TaskRun.getPlayer(event.getPlayer());
        trPlayer.completeTask(Task.CATCH_FISH);
    }

    @EventHandler
    public void onMilkCow(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null || itemInHand.getType() != Material.BUCKET) {
            return;
        }

        if (event.getRightClicked() instanceof Cow) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.MILK_A_COW);
        }
    }

    @EventHandler
    public void onThrowSnowball(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null || itemInHand.getType() != Material.SNOW_BALL) {
            return;
        }

        Action action = event.getAction();
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.THROW_SNOWBALLS);
        }
    }

    @EventHandler
    public void onTNTLight(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null) {
            return;
        }

        if (itemInHand.getType() != Material.FLINT_AND_STEEL) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.hasBlock()) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.TNT) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.LIGHT_TNT_WITH_FLINT);
        }
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() >= 15) {
            TaskRun.getPlayer(event.getPlayer()).completeTask(Task.XP_LEVELS);
        }
    }

    @EventHandler
    public void onStandOnBlock(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        Location locationBelow = new Location(playerLocation.getWorld(), playerLocation.getX(), playerLocation.getY() - 1, playerLocation.getZ());
        PlayerTR trPlayer = TaskRun.getPlayer(player);
        if (locationBelow.getBlock().getType() == Material.BEDROCK) {
            trPlayer.completeTask(Task.STAND_ON_BEDROCK);
        } else if (locationBelow.getBlock().getType() == Material.DIAMOND_BLOCK) {
            trPlayer.completeTask(Task.STAND_ON_DIAMOND_BLOCK);
        }

        if (playerLocation.getWorld().getEnvironment() == Environment.NETHER && playerLocation.getBlockY() == 1) {
            trPlayer.completeTask(Task.NETHER_Y_1);
        }
    }

    @EventHandler
    public void onBuildLimitReached(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getY() > 256.5) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.BUILD_TO_HEIGHT_LIMIT);
        }
    }

    @EventHandler
    public void onRunXBlocks(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getX() > 512) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.RUN_X);
        }
    }

    @EventHandler
    public void onShearSheep(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null || itemInHand.getType() != Material.SHEARS) {
            return;
        }

        Entity clickedEntity = event.getRightClicked();
        if (!(clickedEntity instanceof Sheep)) {
            return;
        }

        Sheep sheep = (Sheep) clickedEntity;
        if (sheep.isAdult() && !sheep.isSheared()) {
            PlayerTR trPlayer = TaskRun.getPlayer(player);
            trPlayer.completeTask(Task.SHEAR_SHEEP);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        PlayerTR trPlayer = TaskRun.getPlayer(event.getEntity());
        TObjectIntMap<Task> taskList = trPlayer.getTaskList();
        if (taskList == null) {
            return;
        }

        for (Task task : taskList.keySet()) {
            if (task == Task.GATHER_OBSIDIAN || task == Task.COLLECT_BLAZE_RODS) {
                trPlayer.removeTaskProgress(task, Integer.MAX_VALUE);
            }
        }

        if (event.getDeathMessage().contains("lava")) {
            trPlayer.completeTask(Task.DIE_FROM_LAVA);
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (event.getItem().getType() == Material.GOLD_SPADE) {
            PlayerTR trPlayer = TaskRun.getPlayer(event.getEnchanter());
            trPlayer.completeTask(Task.ENCHANT_GOLDEN_SHOVEL);
        }
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        PlayerTR trPlayer = TaskRun.getPlayer((Player) event.getWhoClicked());
        ItemStack item = event.getCurrentItem();
        Material material = item.getType();
        if (material == Material.BED || NMSUtils.isAtLeastOneTwelve() && item.toString().contains("_BED")) {
            trPlayer.completeTask(Task.MAKE_A_BED);
        } else if (material == Material.CAKE || NMSUtils.isAtLeastOneTwelve() && item.toString().contains("CAKE")) {
            trPlayer.completeTask(Task.BAKE_A_CAKE);
        } else if (material == Material.GOLDEN_APPLE) {
            trPlayer.completeTask(Task.MAKE_A_GAPPLE);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEatChicken(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.RAW_CHICKEN) {
            TaskRun.getPlayer(event.getPlayer()).completeTask(Task.EAT_RAW_CHICKEN);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEatStew(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MUSHROOM_SOUP) {
            TaskRun.getPlayer(event.getPlayer()).completeTask(Task.EAT_MUSHROOM_SOUP);
        }
    }
}
