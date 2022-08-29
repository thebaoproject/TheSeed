/*
 * Copyright (c) 2022 the Block Art Online Project contributors
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

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.CustomItem;
import ga.baoproject.theseed.exceptions.UnknownItemID;
import ga.baoproject.theseed.items.AnnealBlade;
import ga.baoproject.theseed.items.BareHand;
import ga.baoproject.theseed.items.ObjectEraser;
import ga.baoproject.theseed.items.VanillaItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ItemUtils {

    /**
     * Gets the {@link CustomItem} object from its itemID.
     *
     * @param itemID the item id.
     * @return the {@link CustomItem} found.
     */
    @NotNull
    public static CustomItem get(String itemID) throws UnknownItemID {
        return switch (itemID) {
            case "sao:anneal_blade":
                yield new AnnealBlade();
            case "sao:object_eraser":
                yield new ObjectEraser();
            default:
                throw new UnknownItemID();
        };
    }

    /**
     * Gets the {@link CustomItem} object from the input {@link ItemStack}.
     *
     * @param item the item to be converted.
     * @return the converted item.
     */
    @NotNull
    public static CustomItem get(@NotNull ItemStack item) {
        Plugin pl = TheSeed.getInstance();
        if (item.getItemMeta() == null) {
            // Bare hand
            return new BareHand();
        }
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        try {
            return get(Objects.requireNonNull(container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING)));
        } catch (UnknownItemID | NullPointerException ignored) {
        }
        // If the item have no ID or invalid ID then it must be a vanilla item.
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        // Five times the item damage to be fair with new items.
        AttributeModifier fiveTimes = new AttributeModifier(
                UUID.randomUUID(),
                "generic.attackDamage",
                5,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1
        );
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, fiveTimes);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return new VanillaItem(item.getType());
    }

    /**
     * Gets the full item name list
     *
     * @return the item name list.
     */
    @Contract(pure = true)
    @NotNull
    public static @Unmodifiable List<String> getItemList() {
        return List.of("sao:anneal_blade", "sao:object_eraser");
    }

    /**
     * Checks if the item belongs to the plugin.
     *
     * @return whether the item is from the plugin or not.
     */
    public static boolean amogus(@NotNull ItemStack item) {
        Plugin pl = TheSeed.getInstance();
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
        TheSeed pl = TheSeed.getInstance();
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
        TheSeed pl = TheSeed.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "cooldownTimestamp"), PersistentDataType.LONG, t);
        item.setItemMeta(meta);
    }

    /**
     * Checks is the item deals any considerable damage.
     *
     * @return whether the item deals any considerable damage or not.
     */
    public static boolean isWeapon(Material item) {
        String name = item.name().toLowerCase(Locale.ROOT);
        return name.contains("sword") || (name.contains("axe") && !name.contains("pickaxe")) || name.contains("shovel") || name.contains("hoe") || name.equals("trident");
    }

    /**
     * Gets the damage dealt from a type of item.
     *
     * @param item the item to get damage.
     * @return the damage the item deals when used.
     */
    public static int getItemDamage(@NotNull Material item) {
        // Looks bad, but uses less RAM than any other way (creating ItemStack, etc)
        return switch (item) {
            case AIR -> 0;
            case WOODEN_SHOVEL, GOLDEN_SHOVEL, STONE_HOE -> 2;
            case STONE_SHOVEL, IRON_HOE -> 3;
            case WOODEN_SWORD, GOLDEN_SWORD, IRON_SHOVEL, DIAMOND_HOE, NETHERITE_HOE -> 4;
            case STONE_SWORD, DIAMOND_SHOVEL -> 5;
            case IRON_SWORD, NETHERITE_SHOVEL -> 6;
            case DIAMOND_SWORD, WOODEN_AXE, GOLDEN_AXE -> 7;
            case NETHERITE_SWORD -> 8;
            case STONE_AXE, IRON_AXE, DIAMOND_AXE -> 9;
            case NETHERITE_AXE -> 10;
            default -> 1;
        };
    }
}
