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

package ga.baoproject.theseed;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.InvalidEntityID;
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
                SeedPlayer p;
                try {
                    if (EntityUtils.impostor(i)) {
                        DebugLogger.debug("The player with name of " + i.getName()
                                + "hasn't been set up yet. Automatically setting up...");
                        SeedPlayer.initialize(i);
                    }
                    p = SeedPlayer.fromPlayer(i);
                    Utils.showHPBar(p);
                    p.renderHealth();
                    p.ensureNoHunger();
                } catch (InvalidEntityData e) {
                    if (i.getHealth() != 0) {
                        DebugLogger.debug("The player with name of " + i.getName()
                                + "have invalid player data (healthbar). Automatically resetting...");
                        SeedPlayer.initialize(i);
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
                SeedPlayer p;
                try {
                    if (EntityUtils.impostor(i)) {
                        DebugLogger.debug("The player with name of " + i.getName()
                                + "hasn't been set up yet. Automatically setting up...");
                        SeedPlayer.initialize(i);
                    }
                    p = SeedPlayer.fromPlayer(i);
                    p.applyRegen();
                } catch (InvalidEntityData e) {
                    if (i.getHealth() != 0) {
                        DebugLogger.debug("The player with name of " + i.getName()
                                + "have invalid player data (regen). Silently ignoring...");
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
                    SeedEntity e;
                    if (!(i instanceof Player)) {
                        try {
                            if (EntityUtils.impostor(i)) {
                                // DebugLogger.debug("Entity with type of " + i.getName() + " hasn't been set up
                                // yet. Automatically setting up...");
                                SeedEntity.initialize(i);
                            }
                            e = SeedEntity.fromEntity(i);
                            try {
                                EntityUtils.get(e.getID());
                                if (EntityUtils.get(e.getID()) instanceof SeedBoss b) {
                                    b.setBase(i);
                                    b.renderHealth();
                                    b.setNameTag();

                                }
                            } catch (InvalidEntityID ignored) {
                                e.renderHealth();
                                e.setNameTag();
                            }
                        } catch (InvalidEntityData ex) {
                            if (i.getHealth() != 0) {
                                // DebugLogger.debug("Entity with type of " + i.getType() + " have invalid
                                // entity data (healthbar). Automatically initializing...");
                                ex.printStackTrace();
                                SeedEntity.initialize(i);
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
                SeedPlayer p;
                try {
                    p = SeedPlayer.fromPlayer(i);
                    ItemStack[] inventory = i.getInventory().getContents();
                    for (int slot = 0; slot < inventory.length; slot++) {
                        ItemStack item = inventory[slot];
                        if (item == null) {
                            continue;
                        }
                        SeedItem ci = ItemUtils.get(item);
                        ItemStack newItem = ci.getItem(p.getLocale());
                        if (!newItem.isSimilar(item)) {
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
                    SeedPlayer p = SeedPlayer.fromPlayer(i);
                    for (ItemStack item : i.getInventory().getArmorContents()) {
                        if (item == null) {
                            continue;
                        }
                        SeedItem ci = ItemUtils.get(item);
                        if (ItemUtils.get(ci.getID()) instanceof SeedArmor ct) {
                            if (List.of(EquipmentSlot.HAND, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)
                                    .contains(ct.getTrigger())) {
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
                    SeedPlayer p = SeedPlayer.fromPlayer(i);
                    List<SeedEffect> effectList = new ArrayList<>();
                    for (SeedEffect e : p.getEffects()) {
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
