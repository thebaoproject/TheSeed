/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.events;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.CustomPlayer;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;


/**
 * Listen to player events
 */
public class PlayerEventHandler {

    /**
     * Lets the plugin handles the damage because it's health system is separated from Minecraft.
     */
    public static void onEntityDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        try {
            CustomPlayer p = CustomPlayer.fromPlayer((Player) event.getEntity());
            int originalDamage = (int) event.getFinalDamage();
            int damage;
            if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
                // Damage inflation, normal health is now 100, not 20.
                // If someone ever had 1000 defense, that means 100% damage reduction.
                // Formula: damage = 5*finalDamage * (100-defense/100)%
                damage = (5 * originalDamage * (100 - p.getDefense() / 100)) / 100;
            } else {
                // Let x be our original damage.
                // x = health/maxHealth * 20 <-> health/maxHealth = x/20 <-> 20*health = maxHealth*x <-> health = maxHealth*x/20
                damage = (int) event.getFinalDamage() * p.getMaxHealth() / 20;
            }
            int healthAfter = p.getHealth() - damage;
            if (healthAfter < 0) {
                healthAfter = 0;
            }
            p.setHealth(healthAfter);
            DebugLogger.debug("Setting " + p.getBase().getName() + "'s health to " + healthAfter);
            event.setCancelled(true);
        } catch (InvalidEntityData e) {
            if (((Player) event.getEntity()).getHealth() != 0) {
                TheSeed.getInstance().getSLF4JLogger().error("Received InvalidPlayerData in the player event listener. THIS SHOULDN'T BE POSSIBLE. Silently ignoring...");
            }
        }
    }


    /**
     * Disables natural regen for players, regeneration is managed by the plugin.
     */
    public static void onEntityRegen(@NotNull EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    /**
     * Due to a bug in minecraft, when the health is equal to the max health set in the
     * attributes, it just renders 20 health when it is in fact, 40, so we have to employ
     * some tricks.
     */
    public static void onJoin(PlayerJoinEvent e) {
        PlayerUtils.fixJoinHealthBar(e.getPlayer());
    }

}
