package me.spike.blockartonline;

import me.spike.blockartonline.commands.GiveItem;
import me.spike.blockartonline.commands.PlayerDataManipulation;
import me.spike.blockartonline.customItems.AnnealBlade;
import me.spike.blockartonline.tabCompleters.giveItemTC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.inventory.ItemStack;
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
        pm.registerEvents(new AnnealBlade(), this);
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
