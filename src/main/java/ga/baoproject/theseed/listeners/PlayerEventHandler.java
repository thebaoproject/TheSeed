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

package ga.baoproject.theseed.listeners;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.api.SeedLogger;
import ga.baoproject.theseed.api.types.SeedPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.utils.PlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
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
            SeedPlayer p = SeedPlayer.fromPlayer((Player) event.getEntity());
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
            SeedLogger.debug("Setting " + p.getBase().getName() + "'s health to " + healthAfter);
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

    public static void onPickUp(EntityPickupItemEvent event) {

        ItemStack pickedUp = event.getItem().getItemStack();
        Player p = (Player) event.getEntity();
        ItemStack[] inventory = p.getInventory().getContents();
        for (int slot = 0; slot < inventory.length; slot++) {
            ItemStack playerItem = inventory[slot];
            if (playerItem == null) {
                continue;
            }
            // TODO - implement stricter item filter
            if (pickedUp.getType().equals(playerItem.getType()) && playerItem.getAmount() + pickedUp.getAmount() <= playerItem.getType().getMaxStackSize()) {
                playerItem.setAmount(playerItem.getAmount() + pickedUp.getAmount());
                p.getInventory().setItem(slot, playerItem);
                event.setCancelled(true);
                event.getItem().setHealth(0);
                break;
            }
        }
    }
}