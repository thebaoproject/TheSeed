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

import me.spike.blockartonline.abc.CustomItem;
import me.spike.blockartonline.exceptions.UnknownItem;
import me.spike.blockartonline.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

/**
 * The /gsi command.
 */
public class GiveItem implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!("gsi".equalsIgnoreCase(label))) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(
                    Component.text("You need to provide an item name!").color(NamedTextColor.RED)
            );
            return true;
        }

        CustomItem item;
        try {
            item = ItemUtils.get(args[0]);
        } catch (UnknownItem e) {
            sender.sendMessage(
                    Component.text("Unknown item!").color(NamedTextColor.RED)
            );
            return true;
        }

        ItemStack itemToGive = item.getItem();
        Player playerToGive;

        if (args.length == 1) {
            playerToGive = (Player) sender;
        } else {
            playerToGive = getServer().getPlayer(args[0]);
            if (playerToGive == null) {
                sender.sendMessage(
                        Component.text("Player:").color(NamedTextColor.RED)
                                .append(Component.space())
                                .append(Component.text(args[0]).color(NamedTextColor.YELLOW))
                                .append(Component.space())
                                .append(Component.text("not found!").color(NamedTextColor.RED))
                );
                return true;
            }
        }

        if (sender.hasPermission("bao.giveItem")) {
            playerToGive.getInventory().addItem(itemToGive);
        } else {
            sender.sendMessage(Component.text("You don't have permission to do that!").color(NamedTextColor.RED));
        }
        return true;
    }
}
