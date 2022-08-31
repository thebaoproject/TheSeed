/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.abc.CustomEntity;
import ga.baoproject.theseed.abc.CustomWeapon;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Yes. A bare hand.
 */
public class BareHand extends CustomWeapon {
    public BareHand() {
        super(Material.AIR);
        super.setDamage(1);
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        int damage = (int) e.getFinalDamage();
        try {
            CustomEntity entity = CustomEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
        } catch (InvalidEntityData ignored) {
        }
    }
}
