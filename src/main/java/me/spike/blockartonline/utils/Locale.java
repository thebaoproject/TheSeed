package me.spike.blockartonline.utils;

import me.spike.blockartonline.BlockArtOnline;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Locale {
    private final HashMap<String, Object> locale = new HashMap<>();

    public Locale(String languageCode) {
        BlockArtOnline pl = BlockArtOnline.getInstance();
        try {
            locale.putAll(new Yaml().load(new FileReader(new File(new File(pl.getDataFolder(), "locales"), languageCode + ".yml"), StandardCharsets.UTF_8)));
        } catch (IOException e) {
            pl.getSLF4JLogger().warn("Could not load your selected language, falling back to English.");
            try {
                locale.putAll(new Yaml().load(new FileReader(new File(pl.getDataFolder(), "locales/en.yml"), StandardCharsets.UTF_8)));
            } catch (IOException ex) {
                pl.getSLF4JLogger().error("Cannot load any language file. The plugin will be disabled as a result.");
                pl.theEnd();
            }
        }
    }

    /**
     * Gets the localized string.
     *
     * @param identifier the string identifier.
     * @param args the arguments.
     * @return the localized string.
     */
    public String get(String identifier, String... args) {
        String source = (String) locale.get(identifier);
        if (source == null) {
            return null;
        }
        for (int i = 0; i < args.length; i++) {
            String current = args[i];
            source = source.replace("{" + i + "}", current);
        }
        return source;
    }

    /**
     * Gets the localized string.
     *
     * @param identifier the string identifier.
     * @return the localized string.
     */
    public String get(String identifier) {
        return (String) locale.get(identifier);
    }


}
