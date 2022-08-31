/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class CustomBoss extends CustomMonster {

    private BossBar bossBar;

    public CustomBoss(EntityType b) {
        super(b);
    }

    @Override
    public void renderHealth() {
        setHealth((int) getBase().getHealth());
        getBossBar().setProgress(((float) getHealth() / getMaxHealth()));
    }

    /**
     * Spawns a boss at a specific location.
     *
     * @param l the location to spawn the boss,
     * @return the {@link Damageable} spawned.
     */
    @NotNull
    @Override
    public Damageable spawnAt(@NotNull Location l) {
        setBase((Damageable) l.getWorld().spawnEntity(l, getBaseType()));
        // Have to write the NBT to each entity spawn.
        getBase().setCustomNameVisible(true);
        setNameTag();
        setID(getID());
        setMaxHealth(getMaxHealth());
        setLevel(getLevel());
        setName(getName());
        setHealth(getHealth());
        setLastHealth(getLastHealth());
        Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(getMaxHealth());
        setBossBar(Bukkit.createBossBar(ChatColor.RED + getName(), BarColor.RED, BarStyle.SOLID));
        getBossBar().setProgress(1);
        return getBase();
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar b) {
        bossBar = b;
    }

    public void onDeath(@NotNull EntityDeathEvent e) {
    }
}
