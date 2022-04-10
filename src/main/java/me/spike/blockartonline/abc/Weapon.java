package me.spike.blockartonline.abc;

import me.spike.blockartonline.Utils;
import org.bukkit.ChatColor;
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
        lore.add(ChatColor.GRAY + "Sát thương: " + ChatColor.RED + "+" + this.damage);
        lore.add(ChatColor.GRAY + "Sức mạnh ra đòn: " + ChatColor.RED + "+" + this.strength);
        lore.add(" ");
        for (Ability a : getAbilities()) {
            lore.add(ChatColor.GOLD + "Kĩ năng đặc biệt: " + a.name + " " + ChatColor.YELLOW + ChatColor.BOLD + Utils.toActionString(a.usage));
            lore.addAll(a.description);
        }
        lore.add(" ");
        lore.add(toRarityString(getRarity()));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }

    public int getStrength() { return strength; }
    public void setStrength(int s) { strength = s; }

    public int getDamage() { return damage; }
    public void setDamage(int d) { damage = d; }

}

