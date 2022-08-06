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
