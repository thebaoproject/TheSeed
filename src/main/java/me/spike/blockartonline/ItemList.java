package me.spike.blockartonline;

import me.spike.blockartonline.abc.Weapon;
import me.spike.blockartonline.customItems.AnnealBlade;

import java.util.Arrays;
import java.util.List;

public class ItemList {

    public static Weapon get(String name) {
        return switch (name) {
            case "anneal_blade" -> new AnnealBlade();
            default -> null;
        };
    }

    public static List<String> getList() {
        return Arrays.asList("anneal_blade");
    }

}
