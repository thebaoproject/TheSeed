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

import ga.baoproject.theseed.api.SeedLogger;
import ga.baoproject.theseed.api.types.SeedEffect;
import ga.baoproject.theseed.api.types.SeedPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            SeedLogger.debug("Received InvalidPlayerData when trying to reduce player mana after using an ability. Ignoring...");
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


    public static void updateScoreboard() {
        try {
            for (Player i : Bukkit.getOnlinePlayers()) {
                // Prepare data
                SeedPlayer p = SeedPlayer.fromPlayer(i);
                List<String> boardDetails = new ArrayList<>(List.of(
                        Utils.color("&7" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"))),
                        Utils.color("  &7" + Utils.getTimeOf(i.getWorld())),
                        "",
                        Utils.color("&d  Active Effects:")
                ));
                for (int effectNum = 0; effectNum <= 3; effectNum++) {
                    try {
                        SeedEffect e = p.getEffects().get(effectNum);
                        boardDetails.add(Utils.color("    &7- " + e.getName() + " (" + e.getDuration() + "s)"));
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                Collections.reverse(boardDetails);


                // Scoreboard
                Scoreboard sb = i.getScoreboard();
                Objective obj = sb.getObjective("sao");
                if (obj != null) {
                    obj.unregister();
                }
                obj = sb.registerNewObjective("sao", Criteria.DUMMY, Component.text(Utils.color("&e&lSWORD ART ONLINE")));
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);

                for (int line = boardDetails.toArray().length; line >= 0; line--) {
                    try {
                        Score l = obj.getScore(boardDetails.get(line));
                        l.setScore(line);
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }

        } catch (InvalidEntityData ignored) {
        }
    }
}
