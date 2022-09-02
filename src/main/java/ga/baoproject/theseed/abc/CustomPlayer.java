/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.Utils;
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
    private int defense;
    private int baseMana;
    private int mana;
    private Locale locale;

    public CustomPlayer(Player p) {
        super(p);
        base = p;
        setName(p.getName());
    }

    /**
     * Setups a player's information.
     *
     * @param p the player to set up.
     * @return the player which has just been set up.
     */
    public static CustomPlayer initialize(Player p) {
        CustomPlayer temp = new CustomPlayer(p);
        temp.setName(p.getName());
        temp.setMaxHealth(100);
        temp.setBaseHealth(100);
        temp.setHealth(100);
        temp.setBaseDefense(100);
        temp.setDefense(100);
        temp.setMaxMana(100);
        temp.setBaseMana(100);
        temp.setMana(100);
        temp.setLocale(Locale.VI_VN);
        return temp;
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
        Integer defense = EntityUtils.readFrom(p, "defense", PersistentDataType.INTEGER);
        Integer health = EntityUtils.readFrom(p, "health", PersistentDataType.INTEGER);
        Integer baseHealth = EntityUtils.readFrom(p, "baseHealth", PersistentDataType.INTEGER);
        Integer lastHealth = EntityUtils.readFrom(p, "lastHealth", PersistentDataType.INTEGER);
        Integer mana = EntityUtils.readFrom(p, "mana", PersistentDataType.INTEGER);
        Integer baseMana = EntityUtils.readFrom(p, "baseMana", PersistentDataType.INTEGER);
        String localeString = EntityUtils.readFrom(p, "locale", PersistentDataType.STRING);
        if (maxHealth == null || maxMana == null || baseDefense == null || defense == null || health == null || mana == null || localeString == null || baseHealth == null || baseMana == null || maxHealth < 0 || maxMana < 0 || baseDefense < 0 || defense < 0 || health < 0 || mana < 0 || baseHealth < 0 || baseMana < 0) {
            DebugLogger.debug("INVALID ENTITY DATA: mh:" + maxHealth + " mm:" + maxMana + " bd:" + baseDefense + " h:" + health + " m:" + mana + " lh:" + lastHealth + " bh:" + baseHealth + " ls:" + localeString);
            throw new InvalidEntityData();
        }
        CustomPlayer output = new CustomPlayer(p);
        output.setBase(p);
        output.setName(p.getName());
        output.setMaxHealth(maxHealth);
        output.setBaseHealth(baseHealth);
        output.setHealth(health);
        output.setBaseDefense(baseDefense);
        output.setDefense(defense);
        output.setMaxMana(maxMana);
        output.setBaseMana(baseMana);
        output.setMana(mana);
        output.setLocale(Locale.fromString(localeString));
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

    public int getDefense() {
        return defense;
    }

    public void setDefense(int d) {
        defense = d;
        EntityUtils.writeTo(getBase(), "defense", d);
    }

    /**
     * Sets the player's health according to the health property stored
     * in the player {@code PersistentDataContainer}
     */
    public void renderHealth() {
        if (getHealth() > getMaxHealth()) {
            setHealth(getMaxHealth());
        }
        // f**k java floating point arithmetic, took a long time to figure out
        double newHealth = ((double) getHealth() / getMaxHealth()) * 40;
        DebugLogger.debug("RENDER: setting " + getBase().getName() + "'s health to " + newHealth);
        if (newHealth <= 0) {
            DebugLogger.debug("Player is designated to die. Setting health to max health... " + getHealth());
            setHealth(getMaxHealth());
            Location spawnPoint = base.getBedSpawnLocation();
            if (spawnPoint == null) {
                spawnPoint = base.getWorld().getSpawnLocation();
            }
            base.teleport(spawnPoint);
            base.sendMessage(Component.text(Utils.color("&7" + new Localized("Bạn đã chết. Đang hồi sinh...", "plugin.messages.player.death").render(getLocale()))));
            for (Player i : Bukkit.getOnlinePlayers()) {
                if (!i.getUniqueId().equals(base.getUniqueId())) {
                    try {
                        CustomPlayer ip = CustomPlayer.fromPlayer(i);
                        i.sendMessage(Component.text(Utils.color("&7" + i.getName() + " " + new Localized("đã chết.", "plugin.messages.death.otherDeath").render(ip.getLocale()))));
                    } catch (InvalidEntityData ignored) {
                    }
                }
            }
            base.playSound(base, Sound.BLOCK_ANVIL_FALL, 100, 1);
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

    public int getBaseMana() {
        return baseMana;
    }

    public void setBaseMana(int m) {
        baseMana = m;
        EntityUtils.writeTo(getBase(), "baseMana", m);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale l) {
        locale = l;
        EntityUtils.writeTo(getBase(), "locale", l.toCode());
    }
}