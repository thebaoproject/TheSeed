/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.utils;

import ga.baoproject.theseed.TheSeed;
import ga.baoproject.theseed.i18n.Locale;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Utility for localization.
 */
public class LocalizationUtils {
    /**
     * Ensures the locale files are present.
     *
     * @param pl the plugin instance.
     */
    public static void ensureLocales(@NotNull TheSeed pl) {
        boolean t = pl.getDataFolder().mkdir();
        File localesDirectory = new File(pl.getDataFolder(), "locales");
        t = localesDirectory.mkdir();
        pl.saveResource("locales/en_US.yml", true);
        pl.saveResource("locales/vi_VN.yml", true);
    }

    /**
     * Gets the locale file from the language provided.
     *
     * @param lang the language to get the locale file.
     * @return the locale file.
     */
    @NotNull
    public static YamlConfiguration getLocale(@NotNull Locale lang) {
        TheSeed pl = TheSeed.getInstance();
        boolean t = pl.getDataFolder().mkdir();
        File localesDirectory = new File(pl.getDataFolder(), "locales");
        return YamlConfiguration.loadConfiguration(new File(localesDirectory, lang.toCode() + ".yml"));
    }
}
