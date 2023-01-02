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

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.SeedItem;
import ga.baoproject.theseed.armor.CoatOfMidnight;
import ga.baoproject.theseed.exceptions.UnknownItemID;
import ga.baoproject.theseed.items.*;
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
     * Gets the {@link SeedItem} object from its itemID.
     *
     * @param itemID the item id.
     * @return the {@link SeedItem} found.
     */
    @NotNull
    public static SeedItem get(String itemID) throws UnknownItemID {
        return switch (itemID) {
            case "sao:anneal_blade" -> new AnnealBlade();
            case "sao:object_eraser" -> new ObjectEraser();
            case "sao:coat_of_midnight" -> new CoatOfMidnight();
            case "sao:healing_crystal" -> new HealingCrystal();
            default -> throw new UnknownItemID();
        };
    }

    /**
     * Gets the {@link SeedItem} object from the input {@link ItemStack}.
     *
     * @param item the item to be converted.
     * @return the converted item.
     */
    @NotNull
    public static SeedItem get(@NotNull ItemStack item) {
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
        return List.of(
                "sao:anneal_blade",
                "sao:object_eraser",
                "sao:coat_of_midnight",
                "sao:healing_crystal"
        );
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
