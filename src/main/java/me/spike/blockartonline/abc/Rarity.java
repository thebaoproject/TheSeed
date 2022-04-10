package me.spike.blockartonline.abc;

import org.bukkit.ChatColor;

public enum Rarity {
    COMMON,
    UNCOMMON,
    RARE,
    LEGENDARY,
    COMMON_SWORD,
    UNCOMMON_SWORD,
    RARE_SWORD,
    LEGENDARY_SWORD,
    SPECIAL,
    HAX;

    public static String toRarityString(Rarity rarity) {
        return switch (rarity) {
            case COMMON -> ChatColor.translateAlternateColorCodes('&', "&l&fTHƯỜNG");
            case UNCOMMON -> ChatColor.translateAlternateColorCodes('&', "&l&aHIẾM");
            case RARE -> ChatColor.translateAlternateColorCodes('&', "&l&9RẤT HIẾM");
            case LEGENDARY -> ChatColor.translateAlternateColorCodes('&', "&l&6THẦN THOẠI");
            case COMMON_SWORD -> ChatColor.translateAlternateColorCodes('&', "&l&fKIẾM THƯỜNG");
            case UNCOMMON_SWORD -> ChatColor.translateAlternateColorCodes('&', "&l&aKIẾM HIẾM");
            case RARE_SWORD -> ChatColor.translateAlternateColorCodes('&', "&l&9KIẾM RẤT HIẾM");
            case LEGENDARY_SWORD -> ChatColor.translateAlternateColorCodes('&', "&6KIẾM THẦN THOẠI");
            case SPECIAL -> ChatColor.translateAlternateColorCodes('&', "&l&dĐẶC BIỆT");
            case HAX -> ChatColor.translateAlternateColorCodes('&', "&l&cĐỒ HACKER");
        };
    }
}
