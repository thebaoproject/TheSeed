/*
 * Copyright (c) 2022 SpikeBonjour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.spike.blockartonline.abc;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.exceptions.InvalidEntityData;
import me.spike.blockartonline.utils.EntityUtils;
import me.spike.blockartonline.utils.Utils;
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
    private int lastHealth;
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
    public static void initialize(@NotNull Damageable p) {
        CustomEntity temp = new CustomEntity(p.getType());
        temp.setBase(p);
        temp.setMaxHealth((int) Objects.requireNonNull(((LivingEntity) p).getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        temp.setHealth(temp.getMaxHealth());
        temp.setLastHealth(temp.getHealth());
        temp.setName(Utils.beautifyName(p.getType().toString()));
        DebugLogger.debug("Name to set for entity initialization: " + temp.getName());
        temp.setLevel(1);
        temp.setID(p.getType().toString().toLowerCase(Locale.ROOT));
        temp.getBase().setCustomNameVisible(true);
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
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = p.getPersistentDataContainer();
        Integer maxHealth = container.get(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER);
        Integer health = container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER);
        Integer lastHealth = container.get(new NamespacedKey(pl, "lastHealth"), PersistentDataType.INTEGER);
        String name = container.get(new NamespacedKey(pl, "name"), PersistentDataType.STRING);
        Integer level = container.get(new NamespacedKey(pl, "level"), PersistentDataType.INTEGER);
        String id = container.get(new NamespacedKey(pl, "id"), PersistentDataType.STRING);

        if (maxHealth == null || health == null || lastHealth == null || name == null || level == null || id == null || maxHealth < 0 || health < 0 || lastHealth < 0) {
            throw new InvalidEntityData();
        }
        CustomEntity output = new CustomEntity(p);
        output.setMaxHealth(maxHealth);
        output.setHealth(health);
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
            Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(h);
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
            if (health < 0) {
                health = 0;
            }
            EntityUtils.writeTo(getBase(), "health", h);
            if (!(getBase() instanceof Player)) {
                getBase().setHealth(health);
            }
        }
    }

    /**
     * Sets the entity's health according to the health property stored
     * in the entity {@code PersistentDataContainer}
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
