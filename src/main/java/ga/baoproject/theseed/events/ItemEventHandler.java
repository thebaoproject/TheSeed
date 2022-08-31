/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.events;

import ga.baoproject.theseed.abc.CustomItem;
import ga.baoproject.theseed.abc.CustomWeapon;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class ItemEventHandler {

    /**
     * Checks if the caught event is from the player using an item of the
     * plugin.
     *
     * @param e the event caught.
     * @return whether the player is using the plugin's item.
     */
    public static boolean isValid(@NotNull PlayerInteractEvent e) {
        return (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                && ItemUtils.amogus(e.getPlayer().getInventory().getItemInMainHand());
    }

    public static void onPlayerUse(PlayerInteractEvent e) {
        if (isValid(e)) {
            if (e.getItem() != null) {
                CustomItem item;
                item = ItemUtils.get(e.getItem());
                item.rightClickAction(e);
            }
        }
    }

    public static void onDamage(@NotNull EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            CustomItem item = ItemUtils.get(((Player) e.getDamager()).getInventory().getItemInMainHand());
            if (item instanceof CustomWeapon) {
                ((CustomWeapon) item).attackAction(e);
            } else {
                DebugLogger.debug("EntityDamagedByEntity damage: " + e.getFinalDamage());
            }
        }
    }
}
