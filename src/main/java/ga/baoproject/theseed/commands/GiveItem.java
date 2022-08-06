/*
 * Copyright (c) 2022 the Block Art Online Project contributors
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
 *
 */

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.abc.CustomItem;
import ga.baoproject.theseed.abc.CustomPlayer;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.UnknownItem;
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
        } catch (UnknownItem e) {
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
