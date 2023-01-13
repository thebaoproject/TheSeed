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

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.abc.SeedPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PlayerUtils {
    public static boolean reduceManaOf(Player p, int amount) {
        SeedPlayer cp;
        try {
            cp = SeedPlayer.fromPlayer(p);
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

    /**
     * Due to a bug in minecraft, when the health is equal to the max health set in the
     * attributes, it just renders 20 health when it is in fact, 40, so we have to employ
     * some tricks.
     */
    public static void fixJoinHealthBar(Player player) {
        try {
            SeedPlayer p = SeedPlayer.fromPlayer(player);
            p.getBase().setHealth(39);
            p.renderHealth();
        } catch (InvalidEntityData ignored) {
        }
    }
}
