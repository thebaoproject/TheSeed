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

