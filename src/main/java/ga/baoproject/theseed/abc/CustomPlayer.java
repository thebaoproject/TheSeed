/*
 * Copyright (c) 2022 the Block Art Online Project contributors
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
 *
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.utils.EntityUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a {@link Player} with added data. Modifying any property of this
 * object will automatically write to the player {@link PersistentDataContainer},
 * which is internally used to store player data.
 */
public class CustomPlayer extends CustomEntity {
    private final Player base;
    private int maxMana;
    private int baseDefense;
    private int mana;

    public CustomPlayer(Player p) {
        super(p);
        base = p;
    }

    /**
     * Setups a player's information.
     *
     * @param p the player to set up.
     */
    public static void initialize(Player p) {
        CustomPlayer temp = new CustomPlayer(p);
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
     * @throws InvalidEntityData when the data stored in the player {@link PersistentDataContainer}
     *                           of the player contains invalid values (null or negative)
     */
    @NotNull
    public static CustomPlayer fromPlayer(@NotNull Player p) throws InvalidEntityData {
        Integer maxHealth = EntityUtils.readFrom(p, "maxHealth", PersistentDataType.INTEGER);
        Integer maxMana = EntityUtils.readFrom(p, "maxMana", PersistentDataType.INTEGER);
        Integer baseDefense = EntityUtils.readFrom(p, "baseDefense", PersistentDataType.INTEGER);
        Integer health = EntityUtils.readFrom(p, "health", PersistentDataType.INTEGER);
        Integer lastHealth = EntityUtils.readFrom(p, "lastHealth", PersistentDataType.INTEGER);
        Integer mana = EntityUtils.readFrom(p, "mana", PersistentDataType.INTEGER);

        if (maxHealth == null || maxMana == null || baseDefense == null || health == null || mana == null || maxHealth < 0 || maxMana < 0 || baseDefense < 0 || health < 0 || mana < 0) {
            DebugLogger.debug(maxHealth + " " + maxMana + " " + baseDefense + " " + health + " " + mana + " " + lastHealth);
            throw new InvalidEntityData();
        }
        CustomPlayer output = new CustomPlayer(p);
        output.setBase(p);
        output.setMaxHealth(maxHealth);
        output.setBaseDefense(baseDefense);
        output.setMaxMana(maxMana);
        output.setHealth(health);
        if (lastHealth == null || lastHealth < 0) {
            output.setLastHealth(health);
        } else {
            output.setLastHealth(lastHealth);
        }
        output.setMana(mana);
        Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(40);
        return output;
    }

    public Player getBase() {
        return base;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int m) {
        maxMana = m;
        EntityUtils.writeTo(getBase(), "maxMana", m);
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public void setBaseDefense(int d) {
        baseDefense = d;
        EntityUtils.writeTo(getBase(), "baseDefense", d);
    }

    /**
     * Sets the player's health according to the health property stored
     * in the player {@code PersistentDataContainer}
     */
    public void renderHealth() {
        // TODO - Second row of heart above
        // TODO - Broadcast custom DeathEvent.
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        // f**k java floating point arithmetic, took a long time to figure out
        double newHealth = ((double) getHealth() / getMaxHealth()) * 40;
        if (newHealth <= 0) {
            DebugLogger.debug("Player is designated to die. Setting health to max health..." + getHealth());
            setHealth(getMaxHealth());
            Location spawnPoint = base.getBedSpawnLocation();
            if (spawnPoint == null) {
                spawnPoint = base.getWorld().getSpawnLocation();
            }
            base.teleport(spawnPoint);
            Bukkit.broadcast(Component.text(base.getName() + " died."));
            base.playSound(base, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
            base.setHealth(20);
            int deaths = base.getStatistic(Statistic.DEATHS);
            base.setStatistic(Statistic.DEATHS, deaths + 1);
        } else {
            base.setHealth(newHealth);
        }
        if (getLastHealth() > getHealth()) {
            DebugLogger.debug("Playing sound...");
            base.playSound(base, Sound.ENTITY_PLAYER_HURT, 100, 1);
        }
        setLastHealth(getHealth());
    }

    /**
     * Resets the player's hunger.
     */
    public void ensureNoHunger() {
        base.setFoodLevel(20);
    }

    public void applyRegen() {
        if (getHealth() < getMaxHealth()) {
            setHealth(getHealth() + 10);
        }
        if (getMana() < getMaxMana()) {
            setMana(getMana() + 10);
        }
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int m) {
        mana = m;
        EntityUtils.writeTo(getBase(), "mana", m);
    }
}