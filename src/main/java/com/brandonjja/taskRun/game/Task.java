package com.brandonjja.taskRun.game;

public enum Task {

    MAKE_A_BED("Make a Bed"),
    TRAVEL_TO_NETHER("Travel to The Nether"),
    GROW_TREE_BONEMEAL("Grow a Tree with Bonemeal"),
    GATHER_OBSIDIAN(8, "Gather 8 Obsidian"),
    KILL_PIGMEN(5, "Kill 5 Zombie Pigmen"),
    CATCH_FISH(2, "Catch 2 Fish"),
    MILK_A_COW("Milk a Cow"),
    THROW_SNOWBALLS(128, "Throw 128 Snowballs"),
    STAND_ON_BEDROCK("Stand on Bedrock")
    ;

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
}
