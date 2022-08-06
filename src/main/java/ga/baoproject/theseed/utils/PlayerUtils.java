/*
 * Copyright (c) 2022 the Block Art Online Project contributors
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
 *
 */

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.abc.CustomPlayer;
import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static boolean reduceManaOf(Player p, int amount) {
        CustomPlayer cp;
        try {
            cp = CustomPlayer.fromPlayer(p);
            int newMana = cp.getMana() - amount;
            if (newMana > 0) {
                cp.setMana(newMana);
                return true;
            } else {
                p.sendMessage(Component.text(ChatColor.RED + new Localized("Bạn không có đủ mana!", "plugin.error.noMana").render(cp.getLocale())));
                p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 100, 1);
                return false;
            }
        } catch (InvalidEntityData e) {
            DebugLogger.debug("Received InvalidPlayerData when trying to reduce player mana after using an ability. Ignoring...");
        }
        return false;
    }
}
