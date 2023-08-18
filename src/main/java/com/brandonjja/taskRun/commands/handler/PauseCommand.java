package com.brandonjja.taskRun.commands.handler;

import com.brandonjja.taskRun.commands.TaskRunCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Function;

public class PauseCommand extends TaskRunCommand implements Listener {

    private static final Function<Player, String> UNFROZEN_MSG = player -> ChatColor.BLUE + "The game has been " + ChatColor.GREEN + ChatColor.BOLD + "unpaused" + ChatColor.BLUE + " by " + player.getName() + ", so you are now " + ChatColor.AQUA + ChatColor.BOLD + "unfrozen!";
    private static final Function<Player, String> FREEZE_MSG = player -> ChatColor.BLUE + "The game has been " + ChatColor.RED + ChatColor.BOLD + "paused" + ChatColor.BLUE + " by " + player.getName() + ", so you are now " + ChatColor.AQUA + ChatColor.BOLD + "frozen!";

    private static boolean frozen = false;

    @Override
    public boolean execute(Player player, String[] args) {
        if (!player.hasPermission("taskrunner.pause")) {
            return true;
        }

        String message = frozen ? UNFROZEN_MSG.apply(player) : FREEZE_MSG.apply(player);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(message);

            for (Entity entity : pl.getNearbyEntities(40, 40, 40)) {
                if (entity instanceof Player) {
                    continue;
                }

                if (!(entity instanceof LivingEntity)) {
                    continue;
                }

                LivingEntity livingEntity = (LivingEntity) entity;
                if (frozen) {
                    livingEntity.removePotionEffect(PotionEffectType.SLOW);
                } else {
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 20));
                }
            }
        }

        toggleGameRule(player);

        frozen = !frozen;
        return true;
    }

    private void toggleGameRule(Player player) {
        player.getWorld().setGameRuleValue("doDaylightCycle", String.valueOf(frozen));
    }

    public void onPlayerMove(PlayerMoveEvent event) {
        if (frozen) {
            event.setCancelled(true);
        }
    }

    public void onDamage(EntityDamageEvent event) {
        if (frozen && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    public void onHungerChange(FoodLevelChangeEvent event) {
        if (frozen) {
            event.setCancelled(true);
        }
    }

    public void onPlayerConsume(PlayerItemConsumeEvent event) {
        if (frozen) {
            event.setCancelled(true);
        }
    }
}
