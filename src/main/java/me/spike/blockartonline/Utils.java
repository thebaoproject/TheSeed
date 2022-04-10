package me.spike.blockartonline;

import me.spike.blockartonline.abc.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

public class Utils {

    public static String toActionString(ItemAbilityUseAction action) {
        return switch (action) {
            case RIGHT_CLICK -> "CHUỘT PHẢI";
            case SNEAK -> "GIỮ SHIFT";
            case DOUBLE_JUMP -> "NHẢY HAI LẦN";
        };
    }

    public static ChatColor itemRarityColor(Rarity r) {
        return switch (r) {
            case COMMON, COMMON_SWORD -> ChatColor.WHITE;
            case UNCOMMON, UNCOMMON_SWORD -> ChatColor.GREEN;
            case RARE, RARE_SWORD -> ChatColor.BLUE;
            case LEGENDARY, LEGENDARY_SWORD -> ChatColor.GOLD;
            case SPECIAL -> ChatColor.DARK_PURPLE;
            case HAX -> ChatColor.RED;
        };
    }

    public static void showHPBar(InternalPlayer p) {
        String message = ChatColor.translateAlternateColorCodes(
                '&',
                "&c" + p.getPlayer().getHealth() + "/" + p.getMaxHealth() + "❤    &a" +
                        p.getBaseDefense() + "\uD83D\uDEE1    &b" + p.getMaxMana() + "/" + p.getMaxMana() + "✏"
        );
        p.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

}
