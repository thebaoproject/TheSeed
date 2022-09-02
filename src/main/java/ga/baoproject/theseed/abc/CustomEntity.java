/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a custom entity.
 */
public class CustomEntity {
    private final EntityType baseType;
    private Damageable base;
    private int maxHealth;
    private int health;
    private int baseHealth;
    private int lastHealth;
    private int baseSpeed;
    private int speed;
    private int level;
    private String id;
    private String name;

    public CustomEntity(EntityType b) {
        baseType = b;
    }

    public CustomEntity(@NotNull Damageable p) {
        baseType = EntityType.PLAYER;
        // Players are not spawn-able
        setBase(p);
    }

    /**
     * Setups an entity's information.
     *
     * @param p the entity to set up.
     */
    public static CustomEntity initialize(@NotNull Damageable p) {
        CustomEntity temp = new CustomEntity(p.getType());
        temp.setBase(p);
        // Five times the health to add difficulty and fairness to vanilla entities.
        temp.setBaseHealth(((int) Objects.requireNonNull(((LivingEntity) p).getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue()));
        temp.setMaxHealth(temp.getBaseHealth() * 5);
        temp.setHealth(temp.getMaxHealth());
        temp.setLastHealth(temp.getMaxHealth());
        temp.setName(Utils.beautifyName(p.getType().toString()));
        temp.setLevel(1);
        temp.setID(p.getType().toString().toLowerCase(Locale.ROOT));
        temp.getBase().setCustomNameVisible(true);
        return temp;
    }

    /**
     * Gets the InternalPlayer object from an entity.
     *
     * @param p the entity to get.
     * @return the InternalPlayer object.
     * @throws InvalidEntityData when the data stored in the entity {@link PersistentDataContainer}
     *                           of the entity contains invalid values (null or negative)
     */
    @NotNull
    public static CustomEntity fromEntity(@NotNull Damageable p) throws InvalidEntityData {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = p.getPersistentDataContainer();
        Integer maxHealth = container.get(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER);
        Integer health = container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER);
        Integer baseHealth = container.get(new NamespacedKey(pl, "baseHealth"), PersistentDataType.INTEGER);
        Integer lastHealth = container.get(new NamespacedKey(pl, "lastHealth"), PersistentDataType.INTEGER);
        String name = container.get(new NamespacedKey(pl, "name"), PersistentDataType.STRING);
        Integer level = container.get(new NamespacedKey(pl, "level"), PersistentDataType.INTEGER);
        String id = container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING);
        if (maxHealth == null || health == null || lastHealth == null || name == null || level == null || id == null || baseHealth == null || maxHealth < 0 || health < 0 || lastHealth < 0 || baseHealth < 0) {
            throw new InvalidEntityData();
        }
        CustomEntity output = new CustomEntity(p);
        output.setMaxHealth(maxHealth);
        output.setHealth(health);
        output.setBaseHealth(baseHealth);
        output.setLastHealth(lastHealth);
        output.setName(name);
        output.setLevel(level);
        output.setID(id);
        return output;
    }

    /**
     * Spawns an entity at a specific location.
     *
     * @param l the location to spawn the entity,
     * @return the {@link Damageable} spawned.
     */
    @NotNull
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
        return getBase();
    }

    public void setNameTag() {
        getEntity().customName(Component.text(Utils.color("&7[Lv." + getLevel() + "] &c" + getName() + " " + "&a" + getHealth() + "&f/&a" + getMaxHealth() + "&c❤ HP")));
    }

    public Damageable getEntity() {
        return base;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int h) {
        maxHealth = h;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "maxHealth", h);
            // Players have their own health system.
            if (!(getBase() instanceof Player)) {
                Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(h);
            }
        }
    }

    public int getLastHealth() {
        return lastHealth;
    }

    public void setLastHealth(int lh) {
        lastHealth = lh;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "lastHealth", lh);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        health = h;

        if (getBase() != null) {
            if (health < 0 || getBase().isDead()) {
                health = 0;
            }
            EntityUtils.writeTo(getBase(), "health", h);
            if (!(getBase() instanceof Player)) {
                try {
                    getBase().setHealth(health);
                } catch (IllegalArgumentException e) {
                    DebugLogger.debug("Entity " + getBase() + "have error when trying to set health. h:" + getHealth() + " mh" + getMaxHealth() + " bh:" + getBaseHealth() + ". Resetting stats...");
                    initialize(getBase());
                }
            }
        }
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public void setBaseHealth(int bh) {
        baseHealth = bh;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "baseHealth", bh);
        }
    }

    /**
     * Sets the entity's health according to its real health
     */
    public void renderHealth() {
        setHealth((int) getBase().getHealth());
    }

    public void applyRegen() {
        if (health == maxHealth) {
            return;
        }
        setHealth(getHealth() + 10);
    }

    public Damageable getBase() {
        return base;
    }

    public void setBase(Damageable base) {
        this.base = base;
    }

    public EntityType getBaseType() {
        return baseType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int l) {
        level = l;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "level", l);
        }
    }

    public String getID() {
        return id;
    }

    public void setID(String i) {
        id = i;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "id", i);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
        if (getBase() != null) {
            EntityUtils.writeTo(getBase(), "name", n);
        }
    }
}
