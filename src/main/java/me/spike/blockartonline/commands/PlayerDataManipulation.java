/*
 * Copyright (c) 2022 SpikeBonjour
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.spike.blockartonline.commands;

import me.spike.blockartonline.abc.CustomPlayer;
import me.spike.blockartonline.exceptions.InvalidPlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
            sender.sendMessage(ChatColor.RED + "Console không thể thực hiện được câu lệnh này.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Chọn gì đó đi chứ.");
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
                            "&c" + ip.getHealth() + "/" + ip.getMaxHealth() + "❤    &a" +
                                    ip.getBaseDefense() + "\uD83D\uDEE1    &b" + ip.getMaxMana() + "/" + ip.getMaxMana() + "✏"
                    );
                    sender.sendMessage(message);
                } catch (InvalidPlayerData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                }
                break;
            case "modify":
                try {
                    CustomPlayer ip = CustomPlayer.fromPlayer((Player) sender);
                    int number = Integer.parseInt(args[2]);
                    switch (args[1].toLowerCase(Locale.ROOT)) {
                        case "health" -> ip.setHealth(number);
                        case "mana" -> ip.setMana(number);
                        case "max_mana" -> ip.setMaxMana(number);
                        case "max_health" -> ip.setMaxHealth(number);
                        case "base_defense" -> ip.setBaseDefense(number);
                        default -> sender.sendMessage(ChatColor.RED + "Unknown option: " + args[1]);
                    }
                } catch (InvalidPlayerData e) {
                    sender.sendMessage(ChatColor.RED + "The player data stored in the player is invalid. You can run /mpd init again to re-initialize.");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "The amount that you have entered is invalid.");
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Câu lệnh mà bạn đã nhập không tồn tại!");
        }
        return true;
    }
}

