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
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.i18n.Locale;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.Utils;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    LEGENDARY,
    COMMON_WEAPON,
    UNCOMMON_WEAPON,
    RARE_WEAPON,
    LEGENDARY_WEAPON,
    SPECIAL,
    HAX;

    /**
     * Converts a {@link Rarity} object to its string representation.
     *
     * @return the converted string.
     */
    @Override
    public String toString() {
        return switch (this) {
            case COMMON -> Utils.color("&f&lTHƯỜNG");
            case UNCOMMON -> Utils.color("&a&lHIẾM");
            case RARE -> Utils.color("&9&lRẤT HIẾM");
            case LEGENDARY -> Utils.color("&6&lTHẦN THOẠI");
            case COMMON_WEAPON -> Utils.color("&f&lVŨ KHÍ THƯỜNG");
            case UNCOMMON_WEAPON -> Utils.color("&a&lVŨ KHÍ HIẾM");
            case RARE_WEAPON -> Utils.color("&9&lVŨ KHÍ RẤT HIẾM");
            case LEGENDARY_WEAPON -> Utils.color("&6&lVŨ KHÍ THẦN THOẠI");
            case SPECIAL -> Utils.color("&d&lĐẶC BIỆT");
            case HAX -> Utils.color("&c&lĐỒ HACKER");
        };
    }

    /**
     * Converts a {@link Rarity} object to its localized string representation.
     *
     * @param l the locale to convert the string into.
     * @return the converted string.
     */
    @NotNull
    public String renderLocalizedString(@NotNull Locale l) {
        return switch (this) {
            case COMMON -> Utils.color("&f&l" + new Localized("THƯỜNG", "plugin.item.rarity.common").render(l));
            case UNCOMMON -> Utils.color("&a&l" + new Localized("HIẾM", "plugin.item.rarity.uncommon").render(l));
            case RARE -> Utils.color("&9&l" + new Localized("RẤT HIẾM", "plugin.item.rarity.rare").render(l));
            case LEGENDARY ->
                    Utils.color("&6&l" + new Localized("THẦN THOẠI", "plugin.item.rarity.legendary").render(l));
            case COMMON_WEAPON ->
                    Utils.color("&f&l" + new Localized("VŨ KHÍ THƯỜNG", "plugin.item.rarity.commonWeapon").render(l));
            case UNCOMMON_WEAPON ->
                    Utils.color("&a&l" + new Localized("VŨ KHÍ HIẾM", "plugin.item.rarity.uncommonWeapon").render(l));
            case RARE_WEAPON ->
                    Utils.color("&9&l" + new Localized("VŨ KHÍ RẤT HIẾM", "plugin.item.rarity.rareWeapon").render(l));
            case LEGENDARY_WEAPON ->
                    Utils.color("&6&l" + new Localized("VŨ KHÍ THẦN THOẠI", "plugin.item.rarity.legendaryWeapon").render(l));
            case SPECIAL -> Utils.color("&d&l" + new Localized("ĐẶC BIỆT", "plugin.item.rarity.special").render(l));
            case HAX -> Utils.color("&c&l" + new Localized("ĐỒ HACKER", "plugin.item.rarity.hax").render(l));
        };
    }

    /**
     * Gets the color of the rarity.
     *
     * @return the rarity color
     */
    @NotNull
    public ChatColor toColor() {
        return switch (this) {
            case COMMON, COMMON_WEAPON -> ChatColor.WHITE;
            case UNCOMMON, UNCOMMON_WEAPON -> ChatColor.GREEN;
            case RARE, RARE_WEAPON -> ChatColor.BLUE;
            case LEGENDARY, LEGENDARY_WEAPON -> ChatColor.GOLD;
            case SPECIAL -> ChatColor.DARK_PURPLE;
            case HAX -> ChatColor.RED;
        };
    }
}
