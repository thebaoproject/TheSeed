package me.spike.blockartonline.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
}
