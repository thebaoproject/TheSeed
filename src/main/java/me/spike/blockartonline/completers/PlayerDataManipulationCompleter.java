package me.spike.blockartonline.completers;

import me.spike.blockartonline.utils.ItemUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class PlayerDataManipulationCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                return List.of("init", "check", "modify");
            } else if (args[0].equalsIgnoreCase("modify")) {
                if (args.length == 2) {
                    return List.of("health", "mana", "base_defense", "max_health", "max_mana");
                } else if (args.length == 3) {
                    return List.of("100", "50", "10", "0");
                }
            }
        }
        return null;
    }

}
