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

package ga.baoproject.theseed.i18n;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a language supported by the plugin.
 */
public enum Locale {
    /*
     * Vietnamese - Vietnam
     */
    VI_VN,
    /**
     * English - United States
     */
    EN_US,
    /**
     * Vietnamese IMproved - Vietnam
     */
    VIM_VN;

    /**
     * Convert locale code to Locale object.
     *
     * @param localeCode the locale code in string.
     * @return the corresponding Locale object.
     */
    @NotNull
    public static Locale fromString(String localeCode) {
        return switch (localeCode.toUpperCase(java.util.Locale.ROOT)) {
            case "EN_US", "EN" -> EN_US;
            case "VIM_VN" -> VIM_VN;
            default -> VI_VN;
        };
    }

    /**
     * Gets the English name of the language.
     *
     * @return the language's English name.
     */
    @Override
    @NotNull
    public String toString() {
        return switch (this) {
            case VI_VN -> "Vietnamese - Vietnam";
            case EN_US -> "English - United States";
            case VIM_VN -> "Vietnamese Improved - Vietnam";
        };
    }

    /**
     * Gets the code name of the language.
     *
     * @return the language code name.
     */
    @NotNull
    public String toCode() {
        return switch (this) {
            case VI_VN -> "vi_VN";
            case EN_US -> "en_US";
            case VIM_VN -> "vim_VN";
        };
    }
}
