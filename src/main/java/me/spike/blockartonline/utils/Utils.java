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

import me.spike.blockartonline.abc.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class Utils {

    /**
     * Gets the localized action string for an item.
     *
     * @param action the action to get its name.
     * @return the action name.
     */
    @NotNull
    public static String toActionString(@NotNull ItemAbilityUseAction action) {
        return switch (action) {
            case RIGHT_CLICK -> "CHUỘT PHẢI";
            case SNEAK -> "GIỮ SHIFT";
            case DOUBLE_JUMP -> "NHẢY HAI LẦN";
        };
    }

    /**
     * Gets the color of the rarity.
     *
     * @param r the rarity to get.
     * @return the rarity color
     */
    @NotNull
    public static ChatColor itemRarityColor(@NotNull Rarity r) {
        return switch (r) {
            case COMMON, COMMON_SWORD -> ChatColor.WHITE;
            case UNCOMMON, UNCOMMON_SWORD -> ChatColor.GREEN;
            case RARE, RARE_SWORD -> ChatColor.BLUE;
            case LEGENDARY, LEGENDARY_SWORD -> ChatColor.GOLD;
            case SPECIAL -> ChatColor.DARK_PURPLE;
            case HAX -> ChatColor.RED;
        };
    }

    /**
     * Show the HP bar on the action bar of the player specified.
     *
     * @param p the player to show the bar to.
     */
    public static void showHPBar(@NotNull InternalPlayer p) {
        String message = ChatColor.translateAlternateColorCodes(
                '&',
                "&c" + p.getPlayer().getHealth() + "/" + p.getMaxHealth() + "❤    &a" +
                        p.getBaseDefense() + "\uD83D\uDEE1    &b" + p.getMaxMana() + "/" + p.getMaxMana() + "✏"
        );
        p.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
