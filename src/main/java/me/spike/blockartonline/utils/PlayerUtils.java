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

package me.spike.blockartonline.utils;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.abc.CustomPlayer;
import me.spike.blockartonline.abc.DebugLogger;
import me.spike.blockartonline.exceptions.InvalidPlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayerUtils {
    /**
     * Checks if the player belongs to the plugin.
     *
     * @return whether the player is set up or not.
     */
    public static boolean amogus(@NotNull Player player) {
        Plugin pl = BlockArtOnline.getInstance();
        PersistentDataContainer container = player.getPersistentDataContainer();
        return container.get(new NamespacedKey(pl, "health"), PersistentDataType.INTEGER) != null;
    }

    public static boolean reduceManaOf(Player p, int amount) {
        CustomPlayer cp;
        try {
            cp = CustomPlayer.fromPlayer(p);
            int newMana = cp.getMana() - amount;
            if (newMana > 0) {
                cp.setMana(newMana);
                return true;
            } else {
                p.sendMessage(Component.text(ChatColor.RED + "Bạn không có đủ Mana!"));
                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                return false;
            }
        } catch (InvalidPlayerData e) {
            DebugLogger.debug("Received InvalidPlayerData when trying to reduce player mana afer using an ability. Ignoring...");
        }
        return false;
    }
}
