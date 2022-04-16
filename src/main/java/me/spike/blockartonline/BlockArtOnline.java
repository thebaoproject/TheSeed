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

import me.spike.blockartonline.commands.GiveItem;
import me.spike.blockartonline.commands.PlayerDataManipulation;
import me.spike.blockartonline.tabCompleters.giveItemTC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class BlockArtOnline extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        Logger l = getLogger();
        l.log(Level.INFO, "=----------- Block Art Online -----------=");
        l.log(Level.INFO, ChatColor.AQUA + "by SpikeBonjour. This program is Free Software, licensed under MIT License.");
        l.log(Level.INFO, ChatColor.ITALIC + "This, might be a game, but it isn't something you play.");
        l.log(Level.INFO, ChatColor.BOLD + "                               - Kayaba Akihiko - ");
        l.log(Level.INFO, "=----------------------------------------=");
        l.log(Level.INFO, "Registering event listeners...");
        registerEvents();
        l.log(Level.INFO, "Registering commands...");
        registerCommands();
        l.log(Level.INFO, "Checking database...");
        try {
            boolean result = getDataFolder().mkdirs();
            if (!result && !getDataFolder().exists()) {
                l.log(Level.SEVERE, "Cannot create player data folder.");
            }
        } catch (SecurityException e) {
            l.log(Level.SEVERE, "Cannot create player data folder: insufficient permission.");
            e.printStackTrace();
        }
        PlayerStorage.setup();
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ItemEventHandler(), this);
    }

    public void registerCommands() {
        Logger l = getLogger();
        try {
            PluginCommand gsi = getCommand("gsi");
            PluginCommand mpd = getCommand("mpd");
            assert gsi != null;
            assert mpd != null;
            gsi.setExecutor(new GiveItem());
            gsi.setTabCompleter(new giveItemTC());
            mpd.setExecutor(new PlayerDataManipulation());
        } catch (Exception e) {
            l.log(Level.SEVERE, "Failed to register command.");
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin is shutting down...");
        getLogger().log(Level.INFO, "Plugin shut down successfully");
    }
}
