/*
 * Copyright 2022-2023 SpikeBonjour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ga.baoproject.theseed.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public static void onPlayerJoin(PlayerJoinEvent e) {
        PlayerEventHandler.onJoin(e);
    }
}
