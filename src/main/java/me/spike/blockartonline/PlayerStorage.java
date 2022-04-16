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

import me.spike.blockartonline.abc.InternalPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class PlayerStorage {
    private static FileConfiguration database;
    private static File playerDataFile;

    /**
     * Setups the player data storage. If it doesn't exist, create it. If it exists, load it.
     */
    public static void setup() {
        File playerDataDirectory = new File(Objects.requireNonNull(getServer().getPluginManager().getPlugin("BlockArtOnline")).getDataFolder(), "data");
        boolean success = playerDataDirectory.mkdirs();
        playerDataFile = new File(playerDataDirectory, "players.yml");
        if (!playerDataFile.exists()) {
            try {
                boolean s2 = playerDataFile.createNewFile();
                if (!s2) {
                    getLogger().log(Level.SEVERE, "Failed to create player data file.");
                }
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, "Creating the player data file failed.");
                e.printStackTrace();
            }
        }

        if (!success && !playerDataDirectory.exists()) {
            getLogger().log(Level.SEVERE, "Failed to initialize the players data.");
        } else {
            database = YamlConfiguration.loadConfiguration(playerDataFile);
        }
    }

    public static void save() {
        try {
            database.save(playerDataFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Cannot save the player data file.");
            e.printStackTrace();
        }
        reload();
    }

    public static FileConfiguration getDatabase() { return database; }


    /**
     * Gets the {@link InternalPlayer} object from the Database.
     *
     * @param p the player to get the data from
     * @return the {@link InternalPlayer} object
     */
    public static InternalPlayer getPlayer(Player p) {
        Object a = database.get(p.getUniqueId().toString());
        if (a instanceof HashMap<?,?>) {
            try {
                HashMap<String, Object> newA = (HashMap<String, Object>) a;
                InternalPlayer output = new InternalPlayer();
                output.setPlayer(p);
                output.setBaseDefense((Integer) newA.get("baseDefense"));
                output.setMaxHealth((Integer) newA.get("maxHealth"));
                output.setMaxMana((Integer) newA.get("maxMana"));
                return output;
            } catch (ClassCastException e) {
                getLogger().log(Level.SEVERE, "Cannot get the player.");
                e.printStackTrace();
                return null;
            }
        } else {
            getLogger().log(Level.SEVERE, "Error when getting player data.");
            return null;
        }
    }

    /**
     * Reloads the database
     */
    public static void reload() { database = YamlConfiguration.loadConfiguration(playerDataFile); }

}
