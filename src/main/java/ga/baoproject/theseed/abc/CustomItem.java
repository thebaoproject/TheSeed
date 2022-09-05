/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomItem {
    private final Material type;
    private String itemID;
    private String name;
    private List<Ability> abilities;
    private Rarity rarity;

    public CustomItem(Material m) {
        type = m;
    }

    /**
     * Gets the {@link ItemStack} that can be displayed to the player.
     *
     * @return the {@link ItemStack} to show the player
     */
    public ItemStack getItem(Locale l) {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(getRarity().toColor() + getName()));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.space());
        for (Ability a : getAbilities()) {
            lore.add(Component.text(Utils.color("&6" + new Localized("Kĩ năng đặc biệt", "plugin.item.description.specialAbility").render(l) + ": " + a.getName() + " &e&l" + a.getUsage().toLocalizedString().render(l))));
            lore.addAll(Utils.convListStringColor(Arrays.stream(a.getDescription().render(l).split("\n")).toList()));
            if (a.getCost() > 0) {
                lore.add(Component.text(Utils.color("&8" + new Localized("Mana", "plugin.item.description.manaCost").render(l) + ": &3" + a.getCost())));
            }
            if (a.getCooldown() > 0) {
                lore.add(Component.text(Utils.color("&8" + new Localized("Cooldown", "plugin.item.description.cooldown").render(l) + ": &a" + a.getCooldown())));
            }
        }
        lore.add(Component.space());
        lore.add(Component.text(getRarity().renderLocalizedString(l)));
        meta.lore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        TheSeed pl = TheSeed.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "id"), PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Called when the item is right-clicked.
     * Child class must override this method.
     *
     * @param e the {@link PlayerInteractEvent} received.
     */
    public void rightClickAction(PlayerInteractEvent e) {
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getID() {
        return itemID;
    }

    public void setID(String id) {
        itemID = id;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> a) {
        abilities = a;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity r) {
        rarity = r;
    }

    public Material getMaterial() {
        return type;
    }
}
