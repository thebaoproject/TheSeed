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

import ga.baoproject.theseed.api.SeedLogger;
import ga.baoproject.theseed.api.types.SeedItem;
import ga.baoproject.theseed.api.types.SeedWeapon;
import ga.baoproject.theseed.game.items.BareHand;
import ga.baoproject.theseed.utils.ItemUtils;
import org.bukkit.entity.HumanEntity;
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
                SeedItem item;
                item = ItemUtils.get(e.getItem());
                item.rightClickAction(e);
            }
        }
    }

    public static void onDamage(@NotNull EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof HumanEntity) {
            SeedItem item = ItemUtils.get(((HumanEntity) e.getDamager()).getInventory().getItemInMainHand());
            if (item instanceof SeedWeapon) {
                ((SeedWeapon) item).attackAction(e);
            } else {
                SeedLogger.debug("EntityDamagedByEntity damage: " + e.getFinalDamage());
            }
        } else {
            new BareHand().attackAction(e);
        }
    }
}
