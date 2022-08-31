/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.abc.CustomItem;
import ga.baoproject.theseed.abc.CustomPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.UnknownItemID;
import ga.baoproject.theseed.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static java.lang.Integer.parseInt;
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
            sender.sendMessage(ChatColor.RED + "Bạn phải nêu một lựa chọn!");
            return true;
        }

        CustomItem item;
        CustomPlayer player;
        try {
            item = ItemUtils.get(args[0]);
            player = CustomPlayer.fromPlayer((Player) sender);
        } catch (UnknownItemID e) {
            sender.sendMessage("Vật phẩm bạn lựa chọn không tồn tại.");
            return true;
        } catch (InvalidEntityData e) {
            sender.sendMessage("u r corrupt");
            return true;
        }
        ItemStack itemToGive = item.getItem(player.getLocale());
        boolean specifyAmount = false;
        try {
            itemToGive.setAmount(parseInt(args[args.length - 1]));
            specifyAmount = true;
        } catch (NumberFormatException nfe) {
            itemToGive.setAmount(1);
        }
        Player playerToGive;
        if (args.length == 1) {
            playerToGive = (Player) sender;
        } else if ((args.length == 2 && !specifyAmount) || args.length == 3) {
            playerToGive = getServer().getPlayer(args[0]);
            if (playerToGive == null) {
                sender.sendMessage(ChatColor.RED + "Người chơi " + args[0] + " không tồn tại.");
                return true;
            }
        } else if (args.length == 2) {
            playerToGive = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Người chơi bạn nói đến không tồn tại.");
            return true;
        }
        if (sender.hasPermission("bao.giveItem")) {
            playerToGive.getInventory().addItem(itemToGive);
        } else {
            sender.sendMessage(ChatColor.RED + "Bạn không có quyền!");
        }
        return true;
    }
}
