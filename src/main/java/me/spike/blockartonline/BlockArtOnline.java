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

package me.spike.blockartonline;

import me.spike.blockartonline.abc.CustomEntity;
import me.spike.blockartonline.abc.CustomPlayer;
import me.spike.blockartonline.abc.DebugLogger;
import me.spike.blockartonline.commands.PlayerDataManipulation;
import me.spike.blockartonline.commands.SpawnEntity;
import me.spike.blockartonline.completers.GiveItemCompleter;
import me.spike.blockartonline.completers.PlayerDataManipulationCompleter;
import me.spike.blockartonline.completers.SpawnEntityCompleter;
import me.spike.blockartonline.events.CentralEventListener;
import me.spike.blockartonline.exceptions.InvalidEntityData;
import me.spike.blockartonline.utils.EntityUtils;
import me.spike.blockartonline.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.io.File;

import static me.spike.blockartonline.utils.EntityUtils.amogus;
import static org.bukkit.Bukkit.*;

public final class BlockArtOnline extends JavaPlugin implements CommandExecutor {

    private static BlockArtOnline instance;

    public BlockArtOnline() {
        instance = this;
    }

    public static BlockArtOnline getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();
        Logger l = getSLF4JLogger();
        DebugLogger.setEnabled();
        DebugLogger.setPluginInstance(this);
        l.info("=----------- Block Art Online -----------=");
        l.info(ChatColor.AQUA + "by SpikeBonjour. This program is Free Software, licensed under MIT License.");
        l.info(ChatColor.ITALIC + "This, might be a game, but it isn't something you play.");
        l.info(ChatColor.BOLD + "                               - Kayaba Akihiko - ");
        l.info("=----------------------------------------=");
        l.info("Loading configuration options...");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
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
            gsi.setExecutor(new me.spike.blockartonline.commands.GiveItem());
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

    public void ensureLocales() {
        File localeFolder = new File(getDataFolder(), "locales");
        boolean a = localeFolder.mkdir();
        saveResource("locales/en.yml", true);
    }

    public void registerTasks() {
        // Health bar task
        int playerHealthBarTaskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player i : getOnlinePlayers()) {
                CustomPlayer p;
                try {
                    if (!EntityUtils.amogus(i)) {
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
                    if (!amogus(i)) {
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
                            if (!amogus(i)) {
                                DebugLogger.debug("Entity with type of " + i.getName() + " hasn't been set up yet. Automatically setting up...");
                                CustomEntity.initialize(i);
                            }
                            e = CustomEntity.fromEntity(i);
                            e.renderHealth();
                            e.setNameTag();
                        } catch (InvalidEntityData ex) {
                            if (i.getHealth() != 0) {
                                DebugLogger.debug("Entity with type of " + i.getType() + " have invalid entity data (healthbar). Automatically initializing...");
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
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin is shutting down...");
        getLogger().info("Plugin shut down successfully");
    }

    public void theEnd() {
        getPluginManager().disablePlugin(this);
    }
}
