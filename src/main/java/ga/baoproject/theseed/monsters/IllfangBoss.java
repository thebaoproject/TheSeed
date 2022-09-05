/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.monsters;

import ga.baoproject.theseed.abc.CustomBoss;
import ga.baoproject.theseed.abc.DebugLogger;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IllfangBoss extends CustomBoss {
    public IllfangBoss() {
        super(EntityType.WARDEN);
        DebugLogger.debug("Trying to spawn an Illfang the Kobold Lord...");
        setLevel(30);
        setID("sao:illfang_boss");
        setName("Illfang the Kobold Lord");
        setMaxHealth(1000);
        setHealth(1000);
        setLastHealth(1000);
    }

    public void onDeath(@NotNull EntityDeathEvent e) {
        DebugLogger.debug("Got death event");
        e.setDeathSound(Sound.ENTITY_ENDER_DRAGON_DEATH);
        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.DIRT));
        getBossBar().removeAll();
    }

}
