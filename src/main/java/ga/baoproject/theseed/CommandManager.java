/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed;


import ga.baoproject.theseed.commands.GiveItem;
import ga.baoproject.theseed.commands.PlayerDataManipulation;
import ga.baoproject.theseed.commands.SpawnEntity;
import ga.baoproject.theseed.completers.GiveItemCompleter;
import ga.baoproject.theseed.completers.PlayerDataManipulationCompleter;
import ga.baoproject.theseed.completers.SpawnEntityCompleter;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Registers and configure command autocomplete classes.
 */
public class CommandManager {
    public static void registerCommands(@NotNull TheSeed plugin) {
        Logger l = plugin.getSLF4JLogger();
        try {
            PluginCommand gsi = plugin.getCommand("gsi");
            PluginCommand mpd = plugin.getCommand("mpd");
            PluginCommand spe = plugin.getCommand("spe");
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
}
