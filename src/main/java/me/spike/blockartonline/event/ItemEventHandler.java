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

package me.spike.blockartonline;

import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.abc.Weapon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class ItemEventHandler implements Listener {

    /**
     * Checks if the caught event is from the player using an item of the
     * plugin.
     *
     * @param e the event caught.
     * @return whether the player is using the plugin's item.
     */
    public boolean isValid(PlayerInteractEvent e) {
        getLogger().log(Level.INFO, "Checking validity...");
        getLogger().log(Level.INFO, String.valueOf((
                e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                        e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        ) && ItemUtils.amogus(e.getPlayer().getInventory().getItemInMainHand())));
        return (
            e.getAction().equals(Action.RIGHT_CLICK_AIR) ||
            e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
        ) && ItemUtils.amogus(e.getPlayer().getInventory().getItemInMainHand());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerUse(PlayerInteractEvent e) {
        Logger l = getLogger();
        l.log(Level.INFO, "Received PlayerUseEvent.");
        if (isValid(e)) {
            l.log(Level.INFO, "Action is valid.");
            if (e.getItem() != null) {
                CustomItem item = ItemUtils.get(e.getItem());
                if (item != null) {
                    item.rightClickAction(e);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            CustomItem item = ItemUtils.get(((Player) e.getDamager()).getInventory().getItemInMainHand());
            if (item instanceof Weapon) {
                ((Weapon) item).attackAction(e);
            }
        }
    }


}
