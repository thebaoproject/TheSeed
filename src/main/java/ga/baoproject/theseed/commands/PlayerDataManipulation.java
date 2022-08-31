/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.abc.CustomEntity;
import ga.baoproject.theseed.abc.CustomPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
                CustomPlayer temp = CustomPlayer.fromPlayer((Player) sender);
                temp.getBase().sendMessage(ChatColor.RED + new Localized("Chọn gì đó đi chứ!", "plugin.error.noArgs").render(temp.getLocale()));
            } catch (InvalidEntityData ignored) {
                sender.sendMessage(ChatColor.RED + "Please specify arguments.");
            }
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "init":
                CustomPlayer.initialize((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Your player data has been initialized!");
                break;
            case "check":
                try {
                    CustomPlayer ip = CustomPlayer.fromPlayer((Player) sender);
                    String message = ChatColor.translateAlternateColorCodes(
                            '&',
                            "&c" +
                                    ip.getHealth() + "/" + ip.getMaxHealth() + "❤ " + new Localized("HP", "plugin.player.healthCard.health").render(ip.getLocale()) + "    &a" +
                                    ip.getBaseDefense() + "❈ " + new Localized("Phòng thủ", "plugin.player.healthCard.baseDefense").render(ip.getLocale()) + "    &b" +
                                    ip.getMana() + "/" + ip.getMaxMana() + "✏ " + new Localized("Mana", "plugin.player.healthCard.mana").render(ip.getLocale()) + "    &d" +
                                    ip.getLocale().toString()
                    );
                    sender.sendMessage(message);
                } catch (InvalidEntityData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                }
                break;
            case "modify":
                CustomPlayer ip = null;
                try {
                    ip = CustomPlayer.fromPlayer((Player) sender);
                    int number = Integer.parseInt(args[2]);
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "health" -> ip.setHealth(number);
                        case "mana" -> ip.setMana(number);
                        case "max_mana" -> ip.setMaxMana(number);
                        case "max_health" -> ip.setMaxHealth(number);
                        case "base_defense" -> ip.setBaseDefense(number);
                        default -> sender.sendMessage(ChatColor.RED + "Unknown option: " + args[1]);
                    }
                } catch (NumberFormatException e) {
                    if (args[1].equalsIgnoreCase("locale") && ip != null) {
                        ip.setLocale(ga.baoproject.theseed.i18n.Locale.fromString(args[2]));
                    }
                } catch (InvalidEntityData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                    sender.sendMessage(ChatColor.RED + "The amount that you have entered is invalid.");
                }
                break;
            case "reboot":
                for (Damageable entity : ((Player) sender).getWorld().getLivingEntities()) {
                    CustomEntity.initialize(entity);
                }
                break;
            case "killall":
                for (Damageable entity : ((Player) sender).getWorld().getLivingEntities()) {
                    CustomEntity ce = CustomEntity.initialize(entity);
                    ce.setHealth(0);
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Câu lệnh mà bạn đã nhập không tồn tại!");
        }
        return true;
    }
}

