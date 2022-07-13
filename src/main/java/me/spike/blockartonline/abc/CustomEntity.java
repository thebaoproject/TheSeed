/*
 * Copyright (c) 2022 SpikeBonjour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
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
import me.spike.blockartonline.exceptions.InvalidPlayerData;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom entity.
 */
public class CustomEntity {
    private final Damageable base;
    private int maxHealth;
    private int health;
    private int lastHealth;

    public CustomEntity(Damageable e) {
        base = e;
    }

    /**
     * Setups an entity's information.
     *
     * @param p the entity to set up.
     */
    public static void initialize(Damageable p) {
        CustomEntity temp = new CustomEntity(p);
        temp.setMaxHealth(100);
        temp.setHealth(100);
    }

    /**
     * Gets the InternalPlayer object from an entity.
     *
     * @param p the entity to get.
     * @return the InternalPlayer object.
     * @throws InvalidPlayerData when the data stored in the entity {@link PersistentDataContainer}
     *                           of the entity contains invalid values (null or negative)
     */
    @NotNull
    public static CustomPlayer fromEntity(@NotNull Player p) throws InvalidPlayerData {
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = p.getPersistentDataContainer();
        Integer maxHealth = container.get(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER);
        Integer health = container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER);
        Integer lastHealth = container.get(new NamespacedKey(pl, "lastHealth"), PersistentDataType.INTEGER);

        if (maxHealth == null || health == null || lastHealth == null || maxHealth < 0 || health < 0 || lastHealth < 0) {
            throw new InvalidPlayerData();
        }
        CustomPlayer output = new CustomPlayer(p);
        output.setMaxHealth(maxHealth);
        output.setHealth(health);
        output.setLastHealth(lastHealth);
        return output;
    }

    public Damageable getBase() {
        return base;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int h) {
        maxHealth = h;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = base.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER, h);
    }

    public int getLastHealth() {
        return lastHealth;
    }

    public void setLastHealth(int lh) {
        lastHealth = lh;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = base.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "lastHealth"), PersistentDataType.INTEGER, lh);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        health = h;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = base.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER, health);
    }

    /**
     * Sets the entity's health according to the health property stored
     * in the entity {@code PersistentDataContainer}
     */
    public void renderHealth() {
        // TODO - Second row of heart above
        // TODO - Broadcast custom DeathEvent.
        if (health > maxHealth) {
            setHealth(maxHealth);
        }
        // f**k java floating point arithmetic, took a long time to figure out
        double newHealth = ((double) health / maxHealth) * 20;
        if (newHealth <= 0) {
            base.setHealth(0);
        } else {
            base.setHealth(newHealth);
        }
        setLastHealth(health);
    }

    public void applyRegen() {
        if (health == maxHealth) {
            return;
        }
        setHealth(getHealth() + 10);
    }
}
