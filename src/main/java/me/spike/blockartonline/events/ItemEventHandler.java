/*
 * Copyright (c) 2022 SpikeBonjour
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
 */

package me.spike.blockartonline.events;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.abc.DebugLogger;
import me.spike.blockartonline.abc.Weapon;
import me.spike.blockartonline.exceptions.InvalidItemData;
import me.spike.blockartonline.exceptions.UnknownItem;
import me.spike.blockartonline.items.BareHand;
import me.spike.blockartonline.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ItemEventHandler {

    /**
     * Checks if the caught event is from the player using an item of the
     * plugin.
     *
     * @param e the event caught.
     * @return whether the player is using the plugin's item.
     */
    public static boolean isValid(@NotNull PlayerInteractEvent e) {
        Logger l = BlockArtOnline.getInstance().getSLF4JLogger();
        DebugLogger.debug("Checking validity...");
        DebugLogger.debug(String.valueOf((
                e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                        e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        ) && ItemUtils.amogus(e.getPlayer().getInventory().getItemInMainHand())));
        return (
            e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
            e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        ) && ItemUtils.amogus(e.getPlayer().getInventory().getItemInMainHand());
    }

    public static void onPlayerUse(PlayerInteractEvent e) {
        Logger l = BlockArtOnline.getInstance().getSLF4JLogger();
        DebugLogger.debug("Received PlayerUseEvent.");
        if (isValid(e)) {
            DebugLogger.debug("Action is valid.");
            if (e.getItem() != null) {
                CustomItem item = null;
                try {
                    item = ItemUtils.get(e.getItem());
                    item.rightClickAction(e);
                } catch (InvalidItemData ex) {
                    DebugLogger.debug("Received InvalidItemData exception in item event listener. Silently ignoring.");
                } catch (UnknownItem ex) {
                    DebugLogger.debug("Received UnknownItem exception in item event listener. Silently ignoring.");
                }
            }
        }
    }

    public static void onDamage(@NotNull EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            CustomItem item = null;
            try {
                item = ItemUtils.get(((Player) e.getDamager()).getInventory().getItemInMainHand());
            } catch (InvalidItemData ex) {
                DebugLogger.debug("Received InvalidItemData exception. Silently ignoring.");
            } catch (UnknownItem ex) {
                DebugLogger.debug("Received UnknownItem exception. Silently ignoring.");
            }
            if (item instanceof Weapon) {
                ((Weapon) item).attackAction(e);
            }
        }
    }
}
