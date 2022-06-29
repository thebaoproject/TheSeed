/*
 * Copyright (c) 2022 SpikeBonjour
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.spike.blockartonline.abc;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.exceptions.InvalidPlayerData;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Player} with added data. Modifying any property of this
 * object will automatically write to the player {@link PersistentDataContainer},
 * which is internally used to store player data.
 */
public class InternalPlayer {
    private final Player player;
    private int maxHealth;
    private int maxMana;
    private int baseDefense;
    private int health;
    private int lastHealth;
    private int mana;
    private boolean dead;

    public Player getPlayer() {
        return player;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int h) {
        maxHealth = h;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER, h);
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int m) {
        maxMana = m;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "maxMana"), PersistentDataType.INTEGER, m);
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int d) {
        baseDefense = d;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "baseDefense"), PersistentDataType.INTEGER, d);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        health = h;
        if (health <= 0) {
            dead = true;
            health = 0;
        } else {
            dead = false;
        }
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER, health);
    }

    /**
     * Sets the player's health according to the health property stored
     * in the player {@code PersistentDataContainer}
     */
    public void renderHealth() {
        // TODO - Second row of heart above
        if (health > 0 && health <= maxHealth) {
            // f**k java floating point arithmetic, took a long time to figure out
            player.setHealth(((double) health/maxHealth)*20);
            if (lastHealth > health) {
                player.playSound(player, Sound.ENTITY_PLAYER_HURT, 100, 1);
            }
        } else if (health <= 0 && !dead) {
            setHealth(0);
            player.setHealth(0);
            dead = true;
        } else if (dead && player.getHealth() > 0){
            dead = false;
            setHealth(maxHealth);
        } else if (dead) {
            dead = false;
            setHealth(maxHealth);
        }
        lastHealth = health;
    }

    /**
     * Resets the player's hunger.
     */
    public void ensureNoHunger() {
        player.setFoodLevel(20);
    }

    public void applyRegen() {
        if (health == maxHealth) {
            return;
        }
        setHealth(getHealth() + 10);
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int m) {
        mana = m;
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(new NamespacedKey(pl, "mana"), PersistentDataType.INTEGER, m);
    }

    public InternalPlayer(Player p) {
        player = p;
    }

    /**
     * Setups a player's information.
     *
     * @param p the player to set up.
     */
    public static void initialize(Player p) {
        InternalPlayer temp = new InternalPlayer(p);
        temp.setMaxHealth(100);
        temp.setBaseDefense(100);
        temp.setMaxMana(100);
        temp.setHealth(100);
        temp.setMana(100);
    }

    /**
     * Gets the InternalPlayer object from a player.
     *
     * @param p the player to get.
     * @return the InternalPlayer object.
     *
     * @throws InvalidPlayerData when the data stored in the player {@link PersistentDataContainer}
     * of the player contains invalid values (null or negative)
     */
    @NotNull
    public static InternalPlayer fromPlayer(@NotNull Player p) throws InvalidPlayerData {
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = p.getPersistentDataContainer();
        Integer maxHealth = container.get(new NamespacedKey(pl, "maxHealth"), PersistentDataType.INTEGER);
        Integer maxMana = container.get(new NamespacedKey(pl, "maxMana"), PersistentDataType.INTEGER);
        Integer baseDefense = container.get(new NamespacedKey(pl, "baseDefense"), PersistentDataType.INTEGER);
        Integer health = container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER);
        Integer mana = container.get(new NamespacedKey(pl, "mana"), PersistentDataType.INTEGER);

        if (maxHealth == null || maxMana == null || baseDefense == null || health == null || mana == null || maxHealth < 0 || maxMana < 0 || baseDefense < 0 || health < 0 || mana < 0) {
            throw new InvalidPlayerData();
        }
        InternalPlayer output = new InternalPlayer(p);
        output.setMaxHealth(maxHealth);
        output.setBaseDefense(baseDefense);
        output.setMaxMana(maxMana);
        output.setHealth(health);
        output.setMana(mana);
        return output;
    }
}