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

import ga.baoproject.theseed.api.types.SeedPlayer;
import ga.baoproject.theseed.i18n.Localized;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {
    /**
     * Show the HP bar on the action bar of the player specified.
     *
     * @param p the player to show the bar to.
     */
    public static void showHPBar(@NotNull SeedPlayer p) {
        String message = ChatColor.translateAlternateColorCodes(
                '&',
                "&c" +
                        p.getHealth() + "/" + p.getMaxHealth() + "❤ " + new Localized("HP", "plugin.player.healthCard.health").render(p.getLocale()) + "    &a" +
                        p.getDefense() + "❈ " + new Localized("Phòng thủ", "plugin.player.healthCard.baseDefense").render(p.getLocale()) + "    &b" +
                        p.getMana() + "/" + p.getMaxMana() + "✏ " + new Localized("Mana", "plugin.player.healthCard.mana").render(p.getLocale())
        );
        p.getBase().sendActionBar(Component.text(message));
    }

    /**
     * Converts a list of {@code String} into a list of {@code Component}
     *
     * @param l the list to convert.
     * @return the output list.
     */
    @NotNull
    public static List<Component> convListString(@NotNull List<String> l) {
        List<Component> o = new ArrayList<>();
        for (String i : l) {
            o.add(Component.text(i));
        }
        return o;
    }

    /**
     * Turn color codes using {@code &} to the standard character
     * {@code ChatColor.COLOR_CODE}
     *
     * @param input the input.
     * @return the string that has the {@code &} replaced with the {@code ChatColor.COLOR_CODE}
     */
    @NotNull
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Beautifies the name given (RICK_ASTLEY to Rick Astley).
     *
     * @param name the ugly name.
     * @return the beautified name.
     */
    @NotNull
    public static String beautifyName(@NotNull String name) {
        List<String> nameSep = List.of(name.split("_"));
        List<String> result = new ArrayList<>();
        for (String n : nameSep) {
            result.add(n.substring(0, 1).toUpperCase(Locale.ROOT) + n.substring(1).toLowerCase(Locale.ROOT));
        }
        return Strings.join(result, ' ');
    }

    /**
     * Converts a list of {@code String} into a list of {@code Component} with color.
     *
     * @param l the list to convert.
     * @return the output list with color.
     */
    @NotNull
    public static List<Component> convListStringColor(@NotNull List<String> l) {
        List<Component> o = new ArrayList<>();
        for (String i : l) {
            o.add(Component.text(color(i)));
        }
        return o;
    }

    public static @NotNull String getTimeOf(@NotNull World w) {
        float t = (float) w.getTime() / 1000F;
        long hours = (long) Math.floor(t);
        long minutes = (w.getTime() - hours * 1000) / 60;
        String minutesStr = minutes > 12 ? String.valueOf(minutes) : "0" + minutes;
        if (hours > 12) {
            return (hours - 12) + ":" + minutesStr + "pm &b\uD83C\uDF19";
        } else {
            return hours + ":" + minutesStr + Utils.color("am &e☀");
        }
    }
}
