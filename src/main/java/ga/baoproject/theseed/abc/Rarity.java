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

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.utils.Utils;

public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    LEGENDARY,
    COMMON_SWORD,
    UNCOMMON_SWORD,
    RARE_SWORD,
    LEGENDARY_SWORD,
    SPECIAL,
    HAX;

    /**
     * Converts a {@link Rarity} object to its string representation.
     *
     * @param rarity the {@link Rarity} object to convert.
     * @return the converted string.
     */
    public static String toRarityString(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> Utils.color("&f&lTHƯỜNG");
            case UNCOMMON -> Utils.color("&a&lHIẾM");
            case RARE -> Utils.color("&9&lRẤT HIẾM");
            case LEGENDARY -> Utils.color("&6&lTHẦN THOẠI");
            case COMMON_SWORD -> Utils.color("&f&lKIẾM THƯỜNG");
            case UNCOMMON_SWORD -> Utils.color("&a&lKIẾM HIẾM");
            case RARE_SWORD -> Utils.color("&9&lKIẾM RẤT HIẾM");
            case LEGENDARY_SWORD -> Utils.color("&6&lKIẾM THẦN THOẠI");
            case SPECIAL -> Utils.color("&d&lĐẶC BIỆT");
            case HAX -> Utils.color("&c&lĐỒ HACKER");
        };
    }
}
