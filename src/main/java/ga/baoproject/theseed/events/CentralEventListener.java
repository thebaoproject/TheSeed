/*
 * Copyright (c) 2022 the Block Art Online Project contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
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
