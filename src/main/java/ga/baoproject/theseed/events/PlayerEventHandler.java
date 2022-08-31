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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
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
            // TODO - Custom potions
            if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
                // Damage inflation, normal health is now 100, not 20.
                p.setHealth(p.getHealth() - (int) event.getFinalDamage() * 5);
            } else {
                // Let x be our original damage.
                // x = health/maxHealth * 20 <-> health/maxHealth = x/20 <-> 20*health = maxHealth*x <-> health = maxHealth*x/20
                p.setHealth(p.getHealth() - (int) event.getFinalDamage() * p.getMaxHealth() / 20);
            }
            DebugLogger.debug("Setting " + p.getBase().getName() + "'s health to " + (p.getHealth() - (int) event.getFinalDamage()));
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

}
