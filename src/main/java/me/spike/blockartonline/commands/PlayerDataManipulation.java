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

import me.spike.blockartonline.abc.CustomEntity;
import me.spike.blockartonline.abc.CustomPlayer;
import me.spike.blockartonline.exceptions.InvalidEntityData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
            sender.sendMessage(Component.text("This command can't be involved by console or command block!").color(NamedTextColor.RED));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Component.text("You need to provide an option!").color(NamedTextColor.RED));
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "init":
                CustomPlayer.initialize((Player) sender);
                sender.sendMessage(Component.text("Your player data has been initialized").color(NamedTextColor.GREEN));
                break;
            case "check":
                try {
                    CustomPlayer ip = CustomPlayer.fromPlayer((Player) sender);
                    Component message =
                            Component.text(ip.getHealth() + "/" + ip.getMaxHealth() + "❤").color(NamedTextColor.RED)
                                    .append(Component.text("   "))
                                    .append(Component.text(ip.getBaseDefense() + "\uD83D\uDEE1").color(NamedTextColor.GREEN))
                                    .append(Component.text("   "))
                                    .append(Component.text(ip.getMana() + "/" + ip.getMaxMana() + "✏").color(NamedTextColor.AQUA));
                    sender.sendMessage(message);
                } catch (InvalidEntityData e) {
                    sender.sendMessage(
                            Component.text("The player data stored in the player is invalid. You can run /mpd init again to re-initialize.").color(NamedTextColor.RED)
                    );
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
                        default -> sender.sendMessage(
                                Component.text("Unknown option:").color(NamedTextColor.RED)
                                        .append(Component.space())
                                        .append(Component.text(args[1]).color(NamedTextColor.YELLOW))
                        );
                    }
                } catch (InvalidEntityData e) {
                    sender.sendMessage(
                            Component.text("The player data stored in the player is invalid. You can run /mpd init again to re-initialize.").color(NamedTextColor.RED)
                    );
                } catch (NumberFormatException e) {
                    sender.sendMessage(
                            Component.text("The amount that you have entered is invalid.").color(NamedTextColor.RED)
                    );
                }
                break;
            case "reboot":
                for (Damageable entity : ((Player) sender).getWorld().getLivingEntities()) {
                    CustomEntity.initialize(entity);
                }
                break;
            default:
                sender.sendMessage(
                        Component.text("Command").color(NamedTextColor.RED)
                                .append(Component.space())
                                .append(Component.text(args[0]).color(NamedTextColor.YELLOW))
                                .append(Component.space())
                                .append(Component.text("not found!").color(NamedTextColor.RED))
                );
        }
        return true;
    }
}

