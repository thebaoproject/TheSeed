/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class ObjectEraser extends CustomWeapon {

    public ObjectEraser() {
        super(Material.GOLDEN_SWORD);
        super.setID("sao:object_eraser");
        super.setName("Object Eraser");
        super.setDamage(999999999);
        super.setStrength(999999999);
        super.setAbilities(List.of(
                new Ability()
                        .setName(new Localized("Phá hủy", "plugin.itemDetails.ObjectEraser.ability"))
                        .setDescription(new Localized(
                                "&7Giết chết bất ki sinh vật nào chỉ với one hit.",
                                "plugin.itemDetails.ObjectEraser.description"
                        )).setUsage(ItemAbilityUseAction.NONE).setCost(0).setCooldown(0)
        ));
        super.setRarity(Rarity.HAX);
    }

    public void attackAction(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Damageable victim) {
            try {
                CustomEntity entity = CustomEntity.fromEntity(victim);
                entity.setHealth(0);
            } catch (InvalidEntityData ignored) {
            }
        }
    }
}
