package com.brandonjja.taskRun.game;

import com.brandonjja.taskRun.TaskRun;
import com.brandonjja.taskRun.nms.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public enum Task {

    MAKE_A_BED("Make a Bed"),
    TRAVEL_TO_NETHER("Travel to The Nether"),
    GROW_TREE_BONEMEAL("Grow a Tree with Bonemeal"),
    GATHER_OBSIDIAN(8, "Gather 8 Obsidian"),
    KILL_PIGMEN(5, "Kill 5 Zombie Pigmen"),
    CATCH_FISH(2, "Catch 2 Fish"),
    MILK_A_COW("Milk a Cow"),
    THROW_SNOWBALLS(128, "Throw 128 Snowballs"),
    STAND_ON_BEDROCK("Stand on Bedrock"),
    BUILD_TO_HEIGHT_LIMIT("Build to Height Limit"),
    KILL_CREEPERS(15, "Kill 15 Creepers"),
    BUILD_A_SNOWMAN("Build a Snowman"),
    COLLECT_BLAZE_RODS(10, "Collect 10 Blade Rods"),
    STAND_ON_DIAMOND_BLOCK("Stand on a Diamond Block"),
    KILL_GHAST("Kill a Ghast"),
    SHEAR_SHEEP(10, "Shear 10 Sheep"),
    BREAK_GLOWSTONE(20, "Break 20 Glowstone"),
    DIE_FROM_LAVA("Die from Lava"),
    MINE_QUARTZ_ORE(64, "Mine 64 Quartz Ore Blocks"),
    ENCHANT_GOLDEN_SHOVEL("Enchant a Golden Shovel"),
    RUN_X("Run to X: 512"),
    BAKE_A_CAKE("Bake a Cake"),
    EAT_RAW_CHICKEN(10, "Eat 10 Raw Chicken"),
    MAKE_A_GAPPLE("Make a Gapple"),
    BREAK_NETHERRACK(256, "Break 256 Netherrack"),
    LIGHT_TNT_WITH_FLINT("Light TNT with Flint"),
    XP_LEVELS(1, "Get to XP Level 15"),
    EAT_MUSHROOM_SOUP(2, "Eat 2 Mushroom Soup"),
    NETHER_Y_1("Get to Y: 1 in the Nether"),
    KILL_WITHER_SKELETON(2, "Kill 2 Wither Skeletons"),
    BREAK_GLASS(64, "Break 64 Glass"),
    TOUCH_GRASS(1024, "Punch 1024 Grass");

    public static final Task[] VALUES = values();

    private final int neededCompletions; // How many items needed or repetitions required to fully complete this task
    private final String description; // Short title describing this task

    Task(int neededCompletions, String description) {
        this.neededCompletions = neededCompletions;
        this.description = description;
    }

    Task(String description) {
        this(1, description);
    }

    public int getNeededCompletions() {
        return neededCompletions;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Displays a message in chat for all players when a player completes a task
     *
     * @param player the player who completed this Task
     */
    public void announceTaskComplete(Player player) {
        PlayerTR trPlayer = TaskRun.getPlayer(player);
        trPlayer.finishTask();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(ChatColor.AQUA + player.getName() + ChatColor.GREEN + " has completed \"" + getDescription() + "\"");
            onlinePlayer.sendMessage(ChatColor.GOLD + "Tasks completed: " + trPlayer.getFinishedTasks() + "/" + TaskRun.currentGame.getTotalTasksToFinish());
            if (NMSUtils.isAtLeastOneThirteen()) {
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.valueOf("ENTITY_FIREWORK_ROCKET_LARGE_BLAST"), 1, 1);
            } else if (NMSUtils.isAtLeastOneNine()) {
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.valueOf("ENTITY_FIREWORK_LARGE_BLAST"), 1, 1);
            } else {
                onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.FIREWORK_LARGE_BLAST, 1, 1);
            }
        }
        trPlayer.checkEndGame();
    }

    public String toString(int completed) {
        if (completed >= neededCompletions) {
            return ChatColor.RED + ChatColor.STRIKETHROUGH.toString() + description + " " + "(" + completed + "/" + neededCompletions + ")";
        }
        return ChatColor.GREEN + description + " " + ChatColor.GOLD + "(" + completed + "/" + neededCompletions + ")";
    }
}
