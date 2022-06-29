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

package me.spike.blockartonline.utils;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.exceptions.InvalidItemData;
import me.spike.blockartonline.exceptions.UnknownItem;
import me.spike.blockartonline.items.AnnealBlade;
import me.spike.blockartonline.items.BareHand;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ItemUtils {

    /**
     * Gets the {@link CustomItem} object from its itemID.
     *
     * @param itemID the item id.
     * @return the {@link CustomItem} found.
     */
    @Nullable
    public static CustomItem get(String itemID) {
        return switch (itemID) {
            case "anneal_blade" -> new AnnealBlade();
            default -> null;
        };
    }

    /**
     * Gets the {@link CustomItem} object from the input {@link ItemStack}.
     *
     * @param item the item to be converted.
     * @return the converted item.
     *
     * @throws InvalidItemData if the item doesn't belong to the plugin (no {@link PersistentDataContainer}.)
     * @throws UnknownItem when the ID stored in the item is invalid.
     */
    @NotNull
    public static CustomItem get(@NotNull ItemStack item) throws InvalidItemData, UnknownItem {
        Plugin pl = BlockArtOnline.getInstance();
        if (item.getItemMeta() == null) {
            // Bare hand
            return new BareHand();
        }
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (amogus(item)) {
            CustomItem c = get(container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING));
            if (c == null) {
                throw new UnknownItem();
            } else {
                return c;
            }
        } else {
            throw new InvalidItemData();
        }
    }

    /**
     * Gets the full item name list
     *
     * @return the item name list.
     */
    @NotNull
    public static List<String> getList() {
        return Arrays.asList("anneal_blade");
    }

    /**
     * Injects the identifier tag into an item's NBT in order to distinguish it
     * from other vanilla items.
     *
     * @param item the item to inject.
     * @param itemID the ID of the item that needs to be injected.
     * @return the injected item.
     */
    @NotNull
    public static ItemStack injectIdentifier(
            @NotNull ItemStack item,
            @NotNull String itemID
    ) {
        Plugin pl = BlockArtOnline.getInstance();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "id"), PersistentDataType.STRING, itemID);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Checks if the item belongs to the plugin.
     *
     * @return whether the item is from the plugin or not.
     */
    public static boolean amogus(@NotNull ItemStack item) {
        Plugin pl = BlockArtOnline.getInstance();
        if (item.getItemMeta() == null) {
            return false;
        }
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        return container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING) != null;
    }
}
