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
import me.spike.blockartonline.abc.*;
import me.spike.blockartonline.exceptions.InvalidEntityData;
import me.spike.blockartonline.utils.ItemUtils;
import me.spike.blockartonline.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class AnnealBlade extends Weapon {

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
                                        + "&7Gây ra &c" + getDamage() + "&7 sát thương cho bất kì\n"
                                        + "&7sinh vật nào trên đường đi."
                        )
                ).setUsage(ItemAbilityUseAction.RIGHT_CLICK).setCost(30).setCooldown(3)
        ));
        super.setRarity(Rarity.UNCOMMON_SWORD);
    }

    @Override
    public void rightClickAction(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (System.currentTimeMillis() - ItemUtils.getCooldownTimestamp(p.getInventory().getItemInMainHand()) < 3000) {
            p.sendMessage(Component.text(ChatColor.RED + "Anh bạn à, rap chậm thôi!"));
            p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
            return;
        }
        boolean enoughMana = PlayerUtils.reduceManaOf(p, 30);
        if (!enoughMana) {
            return;
        }
        Vector direction = p.getLocation().getDirection();
        Arrow a = p.getWorld().spawnArrow(p.getLocation(), direction, 100, 100);
        a.setPierceLevel(10);
        a.setDamage(35);
        Vector dashMotion = new Vector(direction.getX(), 0, direction.getZ());
        p.setVelocity(dashMotion.multiply(2));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Bạn đã sử dụng khả năng đặc biệt của &dAnneal Blade&6!"));
        ItemUtils.setCooldownTimestamp(p.getInventory().getItemInMainHand(), System.currentTimeMillis());
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        int damage = (int) calculateDamage();
        try {
            CustomEntity entity = CustomEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
            BlockArtOnline.getInstance().getSLF4JLogger().debug("Inflicted " + damage + " on " + entity.getName());
        } catch (InvalidEntityData | ClassCastException ignored) {
        }
    }
}
