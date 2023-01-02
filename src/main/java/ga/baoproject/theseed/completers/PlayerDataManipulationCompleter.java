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
