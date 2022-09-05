/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.completers;

import ga.baoproject.theseed.utils.EffectUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerDataManipulationCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                return List.of("init", "check", "set", "reboot", "killall");
            } else if (args[0].equalsIgnoreCase("set")) {
                if (args.length == 2) {
                    return List.of("health", "mana", "base_defense", "max_health", "max_mana", "locale", "effect");
                } else if (args.length == 3) {
                    if (args[1].equalsIgnoreCase("locale")) {
                        return List.of("vi", "en", "vim");
                    } else if (args[1].equalsIgnoreCase("effect")) {
                        List<String> output = new java.util.ArrayList<>(EffectUtils.getEffectList());
                        output.add("clear");
                        return output;
                    } else {
                        return List.of("100", "50", "10", "0");
                    }
                }
            }
        }
        return null;
    }

}
