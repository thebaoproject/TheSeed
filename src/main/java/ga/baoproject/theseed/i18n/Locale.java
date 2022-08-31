/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
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
