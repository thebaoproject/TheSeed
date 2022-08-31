/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
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
