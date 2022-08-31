/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.ItemUtils;
import ga.baoproject.theseed.utils.PlayerUtils;
import ga.baoproject.theseed.utils.Utils;
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

public class AnnealBlade extends CustomWeapon {

    public AnnealBlade() {
        super(Material.STONE_SWORD);
        super.setID("sao:anneal_blade");
        super.setName("Anneal Blade");
        super.setDamage(35);
        super.setStrength(2);
        super.setAbilities(List.of(
                new Ability().setName(new Localized("Rage Spike", "plugin.itemDetails.AnnealBlade.ability"))
                        .setDescription(new Localized("""
                                &7Tiến thẳng tới trước &a5&7 block, gây ra
                                &7&c35 &7sát thương cho bất kì sinh vật nào\s
                                &7trên đường đi.
                                """, "plugin.itemDetails.AnnealBlade.description"
                        )).setUsage(ItemAbilityUseAction.RIGHT_CLICK).setCost(30).setCooldown(3)
        ));
        super.setRarity(Rarity.UNCOMMON_WEAPON);
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
        p.sendMessage(Utils.color("&6Bạn đã sử dụng khả năng đặc biệt của &dAnneal Blade&6!"));
        ItemUtils.setCooldownTimestamp(p.getInventory().getItemInMainHand(), System.currentTimeMillis());
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        int damage = (int) calculateDamage();
        try {
            CustomEntity entity = CustomEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
            TheSeed.getInstance().getSLF4JLogger().debug("Inflicted " + damage + " on " + entity.getName());
        } catch (InvalidEntityData | ClassCastException ignored) {
        }
    }
}
