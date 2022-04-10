package me.spike.blockartonline.abc;

import me.spike.blockartonline.Utils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.spike.blockartonline.abc.Rarity.toRarityString;

public class CustomItem {
    private ItemStack type;
    private String itemID;
    private String name;
    private List<Ability> abilities;
    private Rarity rarity;

    public ItemStack getItem() {
        ItemMeta meta = this.type.getItemMeta();
        assert meta != null;
        meta.setDisplayName(Utils.itemRarityColor(rarity) + this.name);
        List<String> lore = new ArrayList<>();
        if (this instanceof Weapon) {
            lore.add(ChatColor.GRAY + "Sát thương: " + ChatColor.RED + "+" + ((Weapon) this).getDamage());
            lore.add(ChatColor.GRAY + "Sức mạnh ra đòn: " + ChatColor.RED + "+" + ((Weapon) this).getStrength());
        }
        lore.add(" ");
        for (Ability a : this.abilities) {
            lore.add(ChatColor.GOLD + "Kĩ năng đặc biệt: " + a.name + " " + ChatColor.YELLOW + ChatColor.BOLD + Utils.toActionString(a.usage));
            lore.addAll(a.description);
        }
        lore.add(" ");
        lore.add(toRarityString(this.rarity));
        meta.setLore(lore);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
        this.type.setItemMeta(meta);
        return this.type;
    }

    public String getName() { return name; }
    public void setName(String n) { name = n; }

    public String getID() { return itemID; }
    public void setID(String id) { itemID = id; }

    public List<Ability> getAbilities() { return abilities; }
    public void setAbilities(List<Ability> a) { abilities = a; }

    public Rarity getRarity() { return rarity; }
    public void setRarity(Rarity r) { rarity = r; }

    public ItemStack getBaseItemType() { return type; }
    public void setBaseItemType(ItemStack i) { type = i; }
}
