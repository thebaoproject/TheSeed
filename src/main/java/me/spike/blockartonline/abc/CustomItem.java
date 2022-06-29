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

import me.spike.blockartonline.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.spike.blockartonline.abc.Rarity.toRarityString;

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
    public ItemStack getItem() {
        ItemStack item = new ItemStack(getMaterial());
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(Component.text(Utils.itemRarityColor(getRarity()) + getName()));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.space());
        for (Ability a : getAbilities()) {
            lore.add(Component.text(ChatColor.GOLD + "Kĩ năng đặc biệt: " + a.getName() + " " + ChatColor.YELLOW + ChatColor.BOLD + Utils.toActionString(a.getUsage())));
            lore.addAll(Utils.convListString(a.getDescription()));
        }
        lore.add(Component.space());
        lore.add(Component.text(toRarityString(getRarity())));
        meta.lore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
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

    public final String getName() {
        return name;
    }

    public final void setName(String n) {
        name = n;
    }

    public final String getID() {
        return itemID;
    }

    public final void setID(String id) {
        itemID = id;
    }

    public final List<Ability> getAbilities() {
        return abilities;
    }

    public final void setAbilities(List<Ability> a) {
        abilities = a;
    }

    public final Rarity getRarity() {
        return rarity;
    }

    public final void setRarity(Rarity r) {
        rarity = r;
    }

    public final Material getMaterial() {
        return type;
    }
}
