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

package ga.baoproject.theseed.game.weapons;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.api.types.*;
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

public class AnnealBlade extends SeedWeapon {

    public AnnealBlade() {
        super(Material.STONE_SWORD);
        setID("sao:anneal_blade");
        setName("Anneal Blade");
        setDamage(35);
        setStrength(2);
        setAbilities(List.of(
                new SeedAbility().setName(new Localized("Rage Spike", "plugin.itemDetails.AnnealBlade.ability"))
                        .setDescription(new Localized("""
                                &7Tiến thẳng tới trước &a5&7 block, gây ra
                                &7&c35 &7sát thương cho bất kì sinh vật nào\s
                                &7trên đường đi.
                                """, "plugin.itemDetails.AnnealBlade.description"
                        )).setUsage(SeedAbilityActivation.RIGHT_CLICK).setCost(30).setCooldown(3)
        ));
        setRarity(SeedRarity.UNCOMMON_WEAPON);
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
            SeedEntity entity = SeedEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
            TheSeed.getInstance().getSLF4JLogger().debug("Inflicted " + damage + " on " + entity.getName());
        } catch (InvalidEntityData | ClassCastException ignored) {
        }
    }
}
