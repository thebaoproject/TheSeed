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

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.customItems.AnnealBlade;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Date;
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
     */
    @Nullable
    public static CustomItem get(@NotNull ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        if (amogus(item)) {
            return get(nbt.getCompound("BlockArtOnlineData").getString("id"));
        } else {
            return null;
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
     * @param owner the original owner of the item.
     * @param creationDate the exact time when the item was created.
     * @return the injected item.
     */
    @NotNull
    public static ItemStack injectIdentifier(
            @NotNull ItemStack item,
            @NotNull String itemID,
            @Nullable Player owner,
            @Nullable Date creationDate
    ) {
        NBTItem nbt = new NBTItem(item);
        NBTCompound itemNBT = nbt.addCompound("BlockArtOnlineData");
        itemNBT.setString("id", itemID);
        if (owner != null) {
            itemNBT.setString("ownerID", owner.getUniqueId().toString());
        }
        if (creationDate != null) {
            itemNBT.setString("creationDate", creationDate.toString());
        }
        return nbt.getItem();
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
        return injectIdentifier(item, itemID, null, null);
    }

    /**
     * Injects the owner tag of an existing item.
     *
     * @param item the item to inject.
     * @param p the {@link Player} object of the owner.
     * @return the injected item.
     */
    @NotNull
    public static ItemStack injectOwner(@NotNull ItemStack item, @NotNull Player p) {
        NBTItem nbt = new NBTItem(item);
        NBTCompound itemNBT = nbt.getOrCreateCompound("BlockArtOnlineData");
        itemNBT.setString("ownerID", p.getUniqueId().toString());
        return nbt.getItem();
    }

    /**
     * Injects the creation date of an existing item.
     *
     * @param item the item to inject
     * @param creationDate the creation date.
     * @return the injected item.
     */
    @NotNull
    public static ItemStack injectCreationDate(@NotNull ItemStack item, @NotNull Date creationDate) {
        NBTItem nbt = new NBTItem(item);
        NBTCompound itemNBT = nbt.getOrCreateCompound("BlockArtOnlineData");
        itemNBT.setString("creationDate", creationDate.toString());
        return nbt.getItem();
    }

    /**
     * Checks if the item belongs to the plugin.
     *
     * @return whether the item is from the plugin or not.
     */
    public static boolean amogus(@NotNull ItemStack item) {
        NBTItem nbt = new NBTItem(item);
        return nbt.hasNBTData() && nbt.getCompound("BlockArtOnlineData").hasKey("id");
    }
}
