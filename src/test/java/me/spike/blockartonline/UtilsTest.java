package me.spike.blockartonline;

import me.spike.blockartonline.abc.ItemAbilityUseAction;
import me.spike.blockartonline.abc.Rarity;
import me.spike.blockartonline.utils.Utils;
import org.bukkit.ChatColor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UtilsTest {

    @Test
    public void toActionString() {
        Assertions.assertEquals("CHUỘT PHẢI", Utils.toActionString(ItemAbilityUseAction.RIGHT_CLICK), "Conversion must be correct.");
    }

    @Test
    public void itemRarityColor() {
        Assertions.assertEquals(ChatColor.GOLD, Utils.itemRarityColor(Rarity.LEGENDARY), "Rarity color must be correct.");
    }
}