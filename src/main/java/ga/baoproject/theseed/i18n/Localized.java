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

import ga.baoproject.theseed.utils.LocalizationUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a localized string.
 */
public class Localized {
    private final String fallback;
    private final String id;

    /**
     * Makes a localized string. Text will change depend on user's language.
     *
     * @param fallback the string to return if the language doesn't have that
     *                 string or the user's language doesn't exists.
     * @param id       the id of the string.
     */
    public Localized(@NotNull String fallback, @NotNull String id) {
        this.fallback = fallback;
        this.id = id;
    }

    /**
     * Makes a static string. Text will <b>not</b> change depend on user's language.
     *
     * @param value the string to return.
     */
    public Localized(@NotNull String value) {
        this.fallback = value;
        this.id = null;
    }

    /**
     * Localizes the string.
     *
     * @param locale the locale to turn the string into.
     * @return the localized string.
     */
    public String render(@NotNull Locale locale) {
        if (this.id == null) {
            return fallback;
        }
        YamlConfiguration l = LocalizationUtils.getLocale(locale);
        return (String) l.get(this.id);
    }

    public String getID() {
        return id;
    }

    @Override
    public String toString() {
        return fallback;
    }

}
