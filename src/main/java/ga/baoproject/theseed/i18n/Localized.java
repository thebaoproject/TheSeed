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
