/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed;


import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.UnknownItemID;
import ga.baoproject.theseed.utils.EffectUtils;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.ItemUtils;
import ga.baoproject.theseed.utils.Utils;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.*;

/**
 * Run and manages background jobs.
 */
public class DaemonManager {
    public static void registerTasks(TheSeed plugin) {
        // Health bar task
        int playerHealthBarTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player i : getOnlinePlayers()) {
                CustomPlayer p;
                try {
                    if (EntityUtils.impostor(i)) {
                        DebugLogger.debug("The player with name of " + i.getName() + "hasn't been set up yet. Automatically setting up...");
                        CustomPlayer.initialize(i);
                    }
                    p = CustomPlayer.fromPlayer(i);
                    Utils.showHPBar(p);
                    p.renderHealth();
                    p.ensureNoHunger();
                } catch (InvalidEntityData e) {
                    if (i.getHealth() != 0) {
                        DebugLogger.debug("The player with name of " + i.getName() + "have invalid player data (healthbar). Automatically resetting...");
                        CustomPlayer.initialize(i);
                    }
                }
            }
        }, 1, 1);
        if (playerHealthBarTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling player health bar task failed.");
        }
        // Regen task
        int regenTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player i : getOnlinePlayers()) {
                CustomPlayer p;
                try {
                    if (EntityUtils.impostor(i)) {
                        DebugLogger.debug("The player with name of " + i.getName() + "hasn't been set up yet. Automatically setting up...");
                        CustomPlayer.initialize(i);
                    }
                    p = CustomPlayer.fromPlayer(i);
                    p.applyRegen();
                } catch (InvalidEntityData e) {
                    if (i.getHealth() != 0) {
                        DebugLogger.debug("The player with name of " + i.getName() + "have invalid player data (regen). Silently ignoring...");
                    }
                }
            }
        }, 1, 60);
        if (regenTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling regen task failed.");
        }
        // Mob health bar
        int mobHealthBarTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (World w : getWorlds()) {
                for (Damageable i : w.getLivingEntities()) {
                    CustomEntity e;
                    if (!(i instanceof Player)) {
                        try {
                            if (EntityUtils.impostor(i)) {
//                                DebugLogger.debug("Entity with type of " + i.getName() + " hasn't been set up yet. Automatically setting up...");
                                CustomEntity.initialize(i);
                            }
                            e = CustomEntity.fromEntity(i);
                            e.renderHealth();
                            e.setNameTag();
                        } catch (InvalidEntityData ex) {
                            if (i.getHealth() != 0) {
//                                DebugLogger.debug("Entity with type of " + i.getType() + " have invalid entity data (healthbar). Automatically initializing...");
                                CustomEntity.initialize(i);
                            }
                        }
                    }
                }
            }
        }, 1, 1);
        if (mobHealthBarTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling mob health bar task failed.");
        }
        int itemLoreAndAddBuffTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player i : getOnlinePlayers()) {
                CustomPlayer p;
                try {
                    p = CustomPlayer.fromPlayer(i);
                    ItemStack[] inventory = i.getInventory().getContents();
                    for (int slot = 0; slot < inventory.length; slot++) {
                        ItemStack item = inventory[slot];
                        if (item == null) {
                            continue;
                        }
                        CustomItem ci = ItemUtils.get(item);
                        ItemStack newItem = ci.getItem(p.getLocale());
                        if (!newItem.equals(item)) {
                            newItem.setAmount(item.getAmount());
                            i.getInventory().setItem(slot, newItem);
                        }
                    }
                } catch (InvalidEntityData exc) {
                    DebugLogger.debug("Received InvalidEntityData in item lore task.");
                }
            }
        }, 1, 10);
        if (itemLoreAndAddBuffTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling item lore updating and adding buff task failed.");
        }
        int armorBuffTaskID = getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player i : getOnlinePlayers()) {
                int healthBuff = 0;
                int defenseBuff = 0;
                try {
                    CustomPlayer p = CustomPlayer.fromPlayer(i);
                    for (ItemStack item : i.getInventory().getArmorContents()) {
                        if (item == null) {
                            continue;
                        }
                        CustomItem ci = ItemUtils.get(item);
                        if (ItemUtils.get(ci.getID()) instanceof CustomArmor ct) {
                            if (List.of(EquipmentSlot.HAND, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET).contains(ct.getTrigger())) {
                                // Speed is set by attributes in the item.
                                healthBuff += ct.getHealthBuff();
                                defenseBuff += ct.getProtection();
                            }
                        }
                    }
                    p.setMaxHealth(p.getBaseHealth() + healthBuff);
                    p.setDefense(p.getBaseDefense() + defenseBuff);
                } catch (InvalidEntityData | UnknownItemID ignored) {
                }
            }
        }, 1, 1);
        if (armorBuffTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling armor buff task failed.");
        }
        int potionEffectApplyTaskID = getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player i : getOnlinePlayers()) {
                try {
                    CustomPlayer p = CustomPlayer.fromPlayer(i);
                    List<CustomEffect> effectList = new ArrayList<>();
                    for (CustomEffect e : p.getEffects()) {
                        if (e.getDuration() == 0) {
                            continue;
                        }
                        EffectUtils.getChild(e).applyEffect(p);
                        e.setDuration(e.getDuration() - 1);
                        effectList.add(e);
                    }
                    p.setEffects(effectList);
                } catch (InvalidEntityData ignored) {
                }
            }
        }, 1, 20);
        if (potionEffectApplyTaskID == -1) {
            plugin.getSLF4JLogger().error("Scheduling potion effect task failed.");
        }
    }
}
