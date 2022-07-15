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
import me.spike.blockartonline.exceptions.UnknownItem;
import me.spike.blockartonline.items.AnnealBlade;
import me.spike.blockartonline.items.BareHand;
import me.spike.blockartonline.items.ObjectEraser;
import me.spike.blockartonline.items.VanillaItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Objects;

public class ItemUtils {

    /**
     * Gets the {@link CustomItem} object from its itemID.
     *
     * @param itemID the item id.
     * @return the {@link CustomItem} found.
     */
    @NotNull
    public static CustomItem get(String itemID) throws UnknownItem {
        return switch (itemID) {
            case "anneal_blade":
                yield new AnnealBlade();
            case "object_eraser":
                yield new ObjectEraser();
            default:
                throw new UnknownItem();
        };
    }

    /**
     * Gets the {@link CustomItem} object from the input {@link ItemStack}.
     *
     * @param item the item to be converted.
     * @return the converted item.
     * @throws UnknownItem when the ID stored in the item is invalid.
     */
    @NotNull
    public static CustomItem get(@NotNull ItemStack item) throws UnknownItem {
        Plugin pl = BlockArtOnline.getInstance();
        if (item.getItemMeta() == null) {
            // Bare hand
            return new BareHand();
        }
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (amogus(item)) {
            try {
                return get(container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING));
            } catch (UnknownItem ui) {
                throw new UnknownItem();
            }
        } else {
            return new VanillaItem();
        }
    }

    /**
     * Gets the full item name list
     *
     * @return the item name list.
     */
    @Contract(pure = true)
    @NotNull
    public static @Unmodifiable List<String> getItemList() {
        return List.of("anneal_blade", "object_eraser");
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

    /**
     * Get the item's cooldown time from its NBT.
     *
     * @param item the item to get
     * @return the cooldown timestamp.
     */
    public static long getCooldownTimestamp(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return 0;
        }
        BlockArtOnline pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        Long result = container.get(new NamespacedKey(pl, "cooldownTimestamp"), PersistentDataType.LONG);
        // For item which hasn't had the tag yet.
        return Objects.requireNonNullElse(result, System.currentTimeMillis() - 100000);
    }

    /**
     * Sets the cooldown timestamp for the item.
     *
     * @param item the item to set the cooldown.
     * @param t    the cooldown to set.
     */
    public static void setCooldownTimestamp(@NotNull ItemStack item, long t) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        BlockArtOnline pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "cooldownTimestamp"), PersistentDataType.LONG, t);
        item.setItemMeta(meta);
    }
}
