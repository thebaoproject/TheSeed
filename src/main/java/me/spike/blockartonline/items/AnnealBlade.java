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

package me.spike.blockartonline.items;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.utils.ItemUtils;
import me.spike.blockartonline.abc.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class AnnealBlade extends Weapon {

    @Override
    public void rightClickAction(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        long playerZ = Math.round(p.getLocation().getDirection().getZ());
        List<Entity> nearbyEntities = p.getNearbyEntities(5, 5, 5);
        // hey want math?
        for (Entity i : nearbyEntities) {
            long erz = Math.round(p.getLocation().toVector().add(i.getLocation().toVector().multiply(-1)).getZ());
            long erx = Math.round(p.getLocation().toVector().add(i.getLocation().toVector().multiply(-1)).getX());
            if (playerZ * erz == erx) {
                // Kill the entity if it is killable.
                if (i instanceof Damageable && !i.isInvulnerable()) {
                    ((Damageable) i).setHealth(0);
                }
            }
        }
        Vector direction = p.getLocation().getDirection();
        Vector dashMotion = new Vector(direction.getX(), 0, direction.getZ());
        p.setVelocity(dashMotion.multiply(5));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Bạn đã sử dụng khả năng đặc biệt của &dAnneal Blade&6!"));
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        if (!e.getEntity().isInvulnerable()) { return; }
        Damageable ne = (Damageable) e.getEntity();
        ne.setHealth(ne.getHealth() + e.getFinalDamage() - calculateDamage());
        BlockArtOnline.getInstance().getSLF4JLogger().debug("Inflicted " + calculateDamage() + " on " + e.getEntity().getType().name());
    }

    public AnnealBlade() {
        super(Material.STONE_SWORD);
        super.setID("anneal_blade");
        super.setName("Anneal Blade");
        super.setDamage(35);
        super.setStrength(2);
        super.setAbilities(List.of(
                new Ability().setName("Phi đao").setDescription("" +
                        ChatColor.translateAlternateColorCodes(
                                '&', "&7Tiến thẳng tới trước &a5&7 block.\n"
                                        + "&7Gây ra &a" + getDamage() + "&7 sát thương cho bất kì\n"
                                        + "&7sinh vật nào trên đường đi."
                        )
                ).setUsage(ItemAbilityUseAction.RIGHT_CLICK)
        ));
        super.setRarity(Rarity.UNCOMMON_SWORD);
    }
}
