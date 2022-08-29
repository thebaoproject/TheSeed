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

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.CustomEntity;
import ga.baoproject.theseed.abc.CustomWeapon;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.abc.Rarity;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.ItemUtils;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents any other items.
 */
public class VanillaItem extends CustomWeapon {

    public VanillaItem(Material baseItem) {
        // the best building material
        super(baseItem);
        String name = baseItem.name().toLowerCase(java.util.Locale.ROOT);
        setID("minecraft:" + name);
        setName(Utils.beautifyName(name));
        int damage = ItemUtils.getItemDamage(getMaterial());
        setDamage(damage * 5);
        setStrength(damage);
        boolean iw = ItemUtils.isWeapon(baseItem);
        if (name.contains("diamond")) {
            if (iw) {
                setRarity(Rarity.UNCOMMON_WEAPON);
            } else {
                setRarity(Rarity.UNCOMMON);
            }
        } else if (name.contains("netherite")) {
            if (iw) {
                setRarity(Rarity.RARE_WEAPON);
            } else {
                setRarity(Rarity.RARE);
            }
        } else {
            if (iw) {
                setRarity(Rarity.COMMON_WEAPON);
            } else {
                setRarity(Rarity.COMMON);
            }
        }
    }

    @Override
    public ItemStack getItem(Locale l) {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(getRarity().toColor() + getName()));
        List<Component> lore = new ArrayList<>();
        // Only shows damage when the item in question is a weapon: swords, axes, shovels and hoes.
        if (ItemUtils.isWeapon(getMaterial())) {
            lore.add(Component.text(Utils.color("&7" + new Localized("Sát thương", "plugin.item.description.damage").render(l) + ": &c" + "+" + getDamage())));
            lore.add(Component.text(Utils.color("&7" + new Localized("Sức mạnh ra đòn", "plugin.item.description.strength").render(l) + ": &c" + "+" + getStrength())));
            lore.add(Component.space());
        }
        lore.add(Component.text(getRarity().renderLocalizedString(l)));
        meta.lore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "id"), PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void attackAction(@NotNull EntityDamageByEntityEvent e) {
        int damage = getDamage();
        try {
            CustomEntity entity = CustomEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
        } catch (InvalidEntityData | ClassCastException ignored) {
            DebugLogger.debug("Received InvalidEntityData when using a vanilla item to attack.");
        }
    }

    @Override
    public void rightClickAction(PlayerInteractEvent e) {
        // Dummy method. This might be called when right-clicking a vanilla item.
    }
}
