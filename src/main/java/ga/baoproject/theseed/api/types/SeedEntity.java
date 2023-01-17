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
import ga.baoproject.theseed.api.SeedLogger;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
public class SeedEntity {
    private final EntityType baseType;
    private Damageable base;
    private int maxHealth;
    private int health;
    private int baseHealth;
    private int lastHealth;
    // TODO - implement speed for entities.
    @SuppressWarnings("unused")
    private int baseSpeed;
    @SuppressWarnings("unused")
    private int speed;
    private int level;
    private String id;
    private String name;

    public SeedEntity(EntityType b) {
        baseType = b;
    }

    public SeedEntity(@NotNull Damageable p) {
        baseType = EntityType.PLAYER;
        // Players are not spawn-able
        setBase(p);
    }

    /**
     * Setups an entity's information.
     *
     * @param p the entity to set up.
     */
    public static SeedEntity initialize(@NotNull Damageable p) {
        SeedEntity temp = new SeedEntity(p.getType());
        temp.setBase(p);
        // Five times the health to add difficulty and fairness to vanilla entities.
        if (EntityUtils.impostor(p)) {
            temp.setMaxHealth(
                    (int) Objects.requireNonNull(p.getType().getDefaultAttributes().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue()
                            * TheSeed.getConfiguration().getInt("gameplay.entity.vanilla-health-multiplier")
            );
        } else {
            temp.setMaxHealth((int) Objects.requireNonNull(p.getType().getDefaultAttributes().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        }
        temp.setBaseHealth(temp.getMaxHealth());
        temp.setHealth(temp.getMaxHealth());
        temp.setLastHealth(temp.getMaxHealth());
        temp.setName(Utils.beautifyName(p.getType().toString()));
        temp.setLevel(1);
        // For clarity purpose, used in the event listeners.
        temp.setID("minecraft:" + p.getType().toString().toLowerCase(Locale.ROOT));
        temp.getBase().setCustomNameVisible(true);
        return temp;
    }

    /**
     * Gets the InternalPlayer object from an entity.
     *
     * @param p the entity to get.
     * @return the InternalPlayer object.
     * @throws InvalidEntityData when the data stored in the entity
     *                           {@link PersistentDataContainer}
     *                           of the entity contains invalid values (null or
     *                           negative)
     */
    @NotNull
    public static SeedEntity fromEntity(@NotNull Damageable p) throws InvalidEntityData {
        Plugin pl = TheSeed.getInstance();
        PersistentDataContainer container = p.getPersistentDataContainer();
        Integer maxHealth = container.get(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER);
        Integer health = container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER);
        Integer baseHealth = container.get(new NamespacedKey(pl, "baseHealth"), PersistentDataType.INTEGER);
        Integer lastHealth = container.get(new NamespacedKey(pl, "lastHealth"), PersistentDataType.INTEGER);
        String name = container.get(new NamespacedKey(pl, "name"), PersistentDataType.STRING);
        Integer level = container.get(new NamespacedKey(pl, "level"), PersistentDataType.INTEGER);
        String id = container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING);
        // Health can be less than 0 because the entity can be dead.
        if (maxHealth == null || health == null || lastHealth == null || name == null || level == null || id == null
                || baseHealth == null || maxHealth < 0 || lastHealth < 0 || baseHealth < 0) {
            Bukkit.broadcast(Component.text("mh" + maxHealth + " h" + health + " lh" +
                    lastHealth + " n" + name + " lv" + level + " id'" + id + "' bh" +
                    baseHealth));
            throw new InvalidEntityData();
        }
        SeedEntity output = new SeedEntity(p);
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
    @SuppressWarnings("all")
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
        setBaseHealth(getBaseHealth());
        Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .setBaseValue(getMaxHealth());
        return getBase();
    }

    public void setNameTag() {
        getEntity().customName(Component.text(Utils.color("&7[Lv." + getLevel() + "] &c" + getName() + " " + "&a"
                + getHealth() + "&f/&a" + getMaxHealth() + "&c❤ HP")));
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
                Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH))
                        .setBaseValue(h);
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
                    SeedLogger.debug("Entity " + getBase() + "have error when trying to set health. h:" + getHealth()
                            + " mh" + getMaxHealth() + " bh:" + getBaseHealth() + ". Resetting stats...");
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
