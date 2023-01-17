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

package ga.baoproject.theseed.api.types;

import ga.baoproject.theseed.exceptions.InvalidEntityData;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class SeedConsumable extends SeedItem {
    public SeedConsumable(Material m) {
        super(m);
    }

    public final void rightClickAction(@NotNull PlayerInteractEvent e) {
        ItemStack newItem = e.getItem();
        if (newItem == null) {
            return;
        }
//        Bukkit.broadcast(Component.text("Setting amount to "  + newItem.getType() + " " + (newItem.getAmount() - 1)));
        newItem.setAmount(newItem.getAmount() -1);
        e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().getHeldItemSlot(), newItem);
        try {
            onConsume(SeedPlayer.fromPlayer(e.getPlayer()));
        } catch (InvalidEntityData ignored) {
        }
    }

    public abstract void onConsume(SeedPlayer p);
}
