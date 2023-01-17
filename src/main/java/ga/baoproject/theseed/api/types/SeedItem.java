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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeedItem {
    private final Material type;
    private String itemID;
    private String name;
    private List<SeedAbility> abilities;
    private SeedRarity rarity;

    public SeedItem(Material m) {
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
        for (SeedAbility a : getAbilities()) {
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

    public List<SeedAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<SeedAbility> a) {
        abilities = a;
    }

    public SeedRarity getRarity() {
        return rarity;
    }

    public void setRarity(SeedRarity r) {
        rarity = r;
    }

    public Material getMaterial() {
        return type;
    }
}
