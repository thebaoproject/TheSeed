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

import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.events.CentralEventListener;
import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.LocalizationUtils;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import static org.bukkit.Bukkit.getPluginManager;

public class TheSeed extends JavaPlugin {

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
        DebugLogger.setEnabled(true);
        l.info(" ----------- The Block Art Online Project | The Seed ----------- ");
        l.info("    Copyright (c) 2022 the Block Art Online Project contributors.  ");
        l.info("    This is free software, licensed under the WTFPL.               ");
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
        l.info("Testing locale en_US " + new Localized("en_US -> FAILED", "plugin.localeCheck").render(Locale.EN_US));
        l.info("Testing locale vi_VN " + new Localized("vi_VN -> FAILED", "plugin.localeCheck").render(Locale.VI_VN));
        l.info("Registering central event listener...");
        registerEvents();
        l.info("Registering commands...");
        CommandManager.registerCommands(this);
        l.info("Starting tasks...");
        DaemonManager.registerTasks(this);
        l.info("Plugin initialization complete." + "(" + (System.currentTimeMillis() - startTime) + "ms)");
    }

    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new CentralEventListener(), this);
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
