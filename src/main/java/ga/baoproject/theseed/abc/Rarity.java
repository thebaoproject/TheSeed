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
