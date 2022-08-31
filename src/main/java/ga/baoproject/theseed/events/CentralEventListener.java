/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * A central place to listen to events and pass it onto individual triggers.
 */
public class CentralEventListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public static void onPlayerUse(PlayerInteractEvent e) {
        ItemEventHandler.onPlayerUse(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onDamage(@NotNull EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            ItemEventHandler.onDamage((EntityDamageByEntityEvent) e);
        }
        PlayerEventHandler.onEntityDamage(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onEntityRegen(@NotNull EntityRegainHealthEvent event) {
        PlayerEventHandler.onEntityRegen(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public static void onDeath(EntityDeathEvent e) {
        EntityEventHandler.onDeath(e);
    }
}
