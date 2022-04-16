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

package me.spike.blockartonline.abc;

import me.spike.blockartonline.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.spike.blockartonline.abc.Rarity.toRarityString;

public class Weapon extends CustomItem {
    private int damage;
    private int strength;

    public float calculateDamage() {
        return (float) damage * (100 + strength)/100;
    }

    public ItemStack getItem() {
        ItemStack item = getBaseItemType();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utils.itemRarityColor(getRarity()) + getName());
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Sát thương: " + ChatColor.RED + "+" + getDamage());
        lore.add(ChatColor.GRAY + "Sức mạnh ra đòn: " + ChatColor.RED + "+" + getStrength());
        lore.add(" ");
        for (Ability a : getAbilities()) {
            lore.add(ChatColor.GOLD + "Kĩ năng đặc biệt: " + a.getName() + " " + ChatColor.YELLOW + ChatColor.BOLD + Utils.toActionString(a.getUsage()));
            lore.addAll(a.getDescription());
        }
        lore.add(" ");
        lore.add(toRarityString(getRarity()));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Called when the item is used to attack other entities.
     * Child class must override this method.
     *
     * @param e the {@link EntityDamageByEntityEvent} received.
     */
    public void attackAction(EntityDamageByEntityEvent e) {}

    public final int getStrength() { return strength; }
    public final void setStrength(int s) { strength = s; }

    public final int getDamage() { return damage; }
    public final void setDamage(int d) { damage = d; }

}

