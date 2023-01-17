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

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SeedWeapon extends SeedItem {
    private int damage;
    private int strength;

    public SeedWeapon(Material i) {
        super(i);
    }

    public float calculateDamage() {
        return (float) damage * (100 + strength) / 100;
    }

    public ItemStack getItem(Locale l) {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(getRarity().toColor() + getName()));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(Utils.color("&7" + new Localized("Sát thương", "plugin.item.description.damage").render(l) + ": &c" + "+" + getDamage())));
        lore.add(Component.text(Utils.color("&7" + new Localized("Sức mạnh ra đòn", "plugin.item.description.strength").render(l) + ": &c" + "+" + getStrength())));
        lore.add(Component.space());
        for (SeedAbility a : getAbilities()) {
            lore.add(Component.text(Utils.color("&6" + new Localized("Kĩ năng đặc biệt", "plugin.item.description.specialAbility").render(l) + ": " + a.getName().render(l) + " &e&l" + a.getUsage().toLocalizedString().render(l))));
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
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "id"), PersistentDataType.STRING, getID());
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Called when the item is used to attack other entities.
     * Child class must override this method.
     *
     * @param e the {@link EntityDamageByEntityEvent} received.
     */
    public void attackAction(EntityDamageByEntityEvent e) {
    }

    public final int getStrength() {
        return strength;
    }

    public final void setStrength(int s) {
        strength = s;
    }

    public final int getDamage() {
        return damage;
    }

    public final void setDamage(int d) {
        damage = d;
    }

}

