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
