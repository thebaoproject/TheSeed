/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.commands.GiveItem;
import ga.baoproject.theseed.commands.PlayerDataManipulation;
import ga.baoproject.theseed.commands.SpawnEntity;
import ga.baoproject.theseed.completers.GiveItemCompleter;
import ga.baoproject.theseed.completers.PlayerDataManipulationCompleter;
import ga.baoproject.theseed.completers.SpawnEntityCompleter;
import ga.baoproject.theseed.events.CentralEventListener;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.UnknownItemID;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.ItemUtils;
import ga.baoproject.theseed.utils.LocalizationUtils;
import ga.baoproject.theseed.utils.Utils;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.*;

public final class TheSeed extends JavaPlugin implements CommandExecutor {

    private static TheSeed instance;

    public TheSeed() {
        instance = this;
    }

    public static TheSeed getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        Logger l = getSLF4JLogger();
        DebugLogger.setEnabled();
        DebugLogger.setPluginInstance(this);
        l.info(" ---------------------------- The Seed --------------------------- ");
        l.info("    Copyright (c) 2022 the Block Art Online Project contributors.  ");
        l.info("    This is free software, licensed under the Do What The F**k You ");
        l.info("    Want To Do Public License.                                     ");
        l.info("                                                                   ");
        l.info("     \"The little seed I planted found purchase in distant networks, ");
        l.info("   where it sprouts its own leaves and branches.\"                   ");
        l.info("                             - Kayaba Akihiko // Sword Art Online  ");
        l.info("                                                                   ");
        l.info(" ----------------------------------------------------------------- ");
        l.info("Loading configuration options...");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        l.info("Loading locales...");
        LocalizationUtils.ensureLocales(this);
        l.info("Testing locale... " + new Localized("CHECK FAILED", "plugin.localeCheck").render(Locale.EN_US));
        l.info("Registering event listeners...");
        registerEvents();
        l.info("Registering commands...");
        registerCommands();
        l.info("Starting tasks...");
        registerTasks();
        l.info("Plugin initialization complete." + "(" + (System.currentTimeMillis() - startTime) + "ms)");
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CentralEventListener(), this);
    }

    public void registerCommands() {
        Logger l = getSLF4JLogger();
        try {
            PluginCommand gsi = getCommand("gsi");
            PluginCommand mpd = getCommand("mpd");
            PluginCommand spe = getCommand("spe");
            assert gsi != null;
            assert mpd != null;
            assert spe != null;
            // Register
            gsi.setExecutor(new GiveItem());
            mpd.setExecutor(new PlayerDataManipulation());
            spe.setExecutor(new SpawnEntity());
            // Tab Completer
            gsi.setTabCompleter(new GiveItemCompleter());
            mpd.setTabCompleter(new PlayerDataManipulationCompleter());
            spe.setTabCompleter(new SpawnEntityCompleter());
        } catch (Exception e) {
            l.error("Failed to register command.");
            e.printStackTrace();
        }
    }

    public void registerTasks() {
        // Health bar task
        int playerHealthBarTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
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
                        DebugLogger.debug("The player with name of " + i.getName() + "have invalid player data (healthbar). Silently ignoring...");
                    }
                }
            }
        }, 1, 1);
        if (playerHealthBarTaskID == -1) {
            getSLF4JLogger().error("Scheduling player health bar task failed.");
        }
        // Regen task
        int regenTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
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
            getSLF4JLogger().error("Scheduling regen task failed.");
        }
        // Mob health bar
        int mobHealthBarTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
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
            getSLF4JLogger().error("Scheduling mob health bar task failed.");
        }
        int itemLoreAndAddBuffTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
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
                        newItem.setAmount(item.getAmount());
                        i.getInventory().setItem(slot, newItem);
                    }
                } catch (InvalidEntityData exc) {
                    DebugLogger.debug("Received InvalidEntityData in item lore task.");
                }
            }
        }, 1, 10);
        if (itemLoreAndAddBuffTaskID == -1) {
            getSLF4JLogger().error("Scheduling item lore updating and adding buff task failed.");
        }
        int applyBuffTaskID = getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player i : getOnlinePlayers()) {
                List<String> ctl = new ArrayList<>();
                int healthBuff = 0;
                try {
                    CustomPlayer p = CustomPlayer.fromPlayer(i);
                    for (ItemStack item : i.getInventory().getArmorContents()) {
                        if (item == null) {
                            continue;
                        }
                        CustomItem ci = ItemUtils.get(item);
                        if (ItemUtils.get(ci.getID()) instanceof CustomTalisman ct) {
                            if (
                                    Arrays.stream(i.getInventory().getArmorContents()).toList().contains(ct.getItem(p.getLocale()))
                                            && ct.getTrigger() == BuffTrigger.WEARING
                            ) {
                                // Speed is set by attributes in the item.
                                healthBuff += ct.getHealthBuff();
                            }
                        }
                    }
                    p.setMaxHealth(p.getBaseHealth() + healthBuff);
                } catch (InvalidEntityData | UnknownItemID ignored) {
                }
            }
        }, 1, 1);
        if (applyBuffTaskID == -1) {
            getSLF4JLogger().error("Scheduling removing buff task failed.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is shutting down...");
        getLogger().info("Plugin shut down successfully");
    }

    public void terminate() {
        getPluginManager().disablePlugin(this);
    }
}
