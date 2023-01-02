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
