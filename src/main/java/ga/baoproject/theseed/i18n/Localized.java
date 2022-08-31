/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
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
