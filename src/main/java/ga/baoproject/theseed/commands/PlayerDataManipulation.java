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

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.api.SeedLogger;
import ga.baoproject.theseed.api.types.SeedEffect;
import ga.baoproject.theseed.api.types.SeedEntity;
import ga.baoproject.theseed.api.types.SeedPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import ga.baoproject.theseed.utils.EffectUtils;
import ga.baoproject.theseed.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class PlayerDataManipulation implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("mpd"))) {
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console cannot execute this command.");
            return true;
        }

        if (args.length == 0) {
            try {
                SeedPlayer temp = SeedPlayer.fromPlayer((Player) sender);
                temp.getBase().sendMessage(ChatColor.RED + new Localized("Chọn gì đó đi chứ!", "plugin.error.noArgs").render(temp.getLocale()));
            } catch (InvalidEntityData ignored) {
                sender.sendMessage(ChatColor.RED + "Please specify arguments.");
            }
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "init" -> {
                SeedPlayer.initialize((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Your player data has been initialized!");
            }
            case "check" -> {
                try {
                    SeedPlayer ip = SeedPlayer.fromPlayer((Player) sender);
                    StringBuilder message = new StringBuilder(Utils.color(
                            "BASE STATS:\n" + "&c" +
                                    ip.getHealth() + "/" + ip.getMaxHealth() + "❤ " + new Localized("HP", "plugin.player.healthCard.health").render(ip.getLocale()) + "    &a" +
                                    ip.getBaseDefense() + "❈ " + new Localized("Phòng thủ", "plugin.player.healthCard.baseDefense").render(ip.getLocale()) + "    &b" +
                                    ip.getMana() + "/" + ip.getMaxMana() + "✏ " + new Localized("Mana", "plugin.player.healthCard.mana").render(ip.getLocale()) + "    &d" +
                                    ip.getLocale().toString()
                                    + "ACTIVE EFFECTS:\n"
                    ));
                    for (SeedEffect i : ip.getEffects()) {
                        message.append("--------------\n");
                        message.append("   name: ").append(i.getName().render(ip.getLocale())).append("\n");
                        message.append("   description: ").append(i.getDescription().render(ip.getLocale())).append("\n");
                        message.append("   duration left: ").append(i.getDuration()).append("s").append("\n");
                        message.append("---------------");
                    }
                    sender.sendMessage(message.toString());
                } catch (InvalidEntityData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                }
            }
            case "set" -> {
                SeedPlayer ip = null;
                try {
                    ip = SeedPlayer.fromPlayer((Player) sender);
                    int number = Integer.parseInt(args[2]);
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "health" -> {
                            ip.setHealth(number);
                            if (number > ip.getMaxHealth()) {
                                ip.setMaxHealth(number);
                            }
                            ip.setBaseHealth(number);
                        }
                        case "mana" -> {
                            ip.setMana(number);
                            if (number > ip.getMaxMana()) {
                                ip.setMaxMana(number);
                            }
                            ip.setBaseMana(number);
                        }
                        case "base_defense" -> ip.setBaseDefense(number);
                        default ->
                                sender.sendMessage(ChatColor.RED + new Localized("Bạn đưa ra tham số không hợp lệ:", "plugin.error.invalidArg").render(ip.getLocale()) + " " + args[1]);
                    }
                } catch (NumberFormatException e) {
                    if (args[1].equalsIgnoreCase("locale") && ip != null) {
                        ip.setLocale(ga.baoproject.theseed.i18n.Locale.fromString(args[2]));
                    } else if (args[1].equalsIgnoreCase("effect") && ip != null) {
                        SeedEffect nEffect = EffectUtils.get(args[2]);
                        try {
                            if (args.length < 4) {
                                nEffect.setDuration(20);
                            } else {
                                nEffect.setDuration(Integer.parseInt(args[3]));
                            }
                            ip.addEffect(nEffect);
                            SeedLogger.debug("Current effect" + ip.getEffects());
                        } catch (NumberFormatException exc) {
                            if (args[3].equals("clear")) {
                                ip.setEffects(List.of());
                            } else {
                                sender.sendMessage(ChatColor.RED + new Localized("Bạn đưa ra tham số không hợp lệ:", "plugin.error.invalidArg").render(ip.getLocale()) + " " + args[3]);
                            }
                        }
                    }
                } catch (InvalidEntityData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                }
            }
            case "reboot" -> {
                for (Damageable entity : ((Player) sender).getWorld().getLivingEntities()) {
                    SeedEntity.initialize(entity);
                }
            }
            case "killall" -> {
                for (Damageable entity : ((Player) sender).getWorld().getLivingEntities()) {
                    SeedEntity ce = SeedEntity.initialize(entity);
                    ce.setHealth(0);
                }
            }
            default -> sender.sendMessage(ChatColor.RED + "Câu lệnh mà bạn đã nhập không tồn tại!");
        }
        return true;
    }
}

