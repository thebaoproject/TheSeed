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
            PluginCommand gen = plugin.getCommand("sysgen");
            assert gsi != null;
            assert mpd != null;
            assert spe != null;
            assert gen != null;
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
