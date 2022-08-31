/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import org.bukkit.plugin.Plugin;

/**
 * Reinventing the wheel.
 */
public class DebugLogger {

    private static Plugin pl;
    private static boolean enabled;


    public static void debug(String message) {
        pl.getSLF4JLogger().info("[DEBUG] " + message);
    }

    /**
     * Sets the plugin instance which is used to log the messages.
     *
     * @param plugin the plugin instance.
     */
    public static void setPluginInstance(Plugin plugin) {
        pl = plugin;
    }

    /**
     * Toggles the debug logger.
     *
     * @param e whether to turn on or off the logger.
     */
    public static void setEnabled(boolean e) {
        enabled = e;
    }

    /**
     * Turns on the debug logger.
     */
    public static void setEnabled() {
        enabled = false;
    }
}

