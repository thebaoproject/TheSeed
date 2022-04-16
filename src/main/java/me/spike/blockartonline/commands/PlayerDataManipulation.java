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

import me.spike.blockartonline.PlayerStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PlayerDataManipulation implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("mpd"))) { return true; }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Console không thể thực hiện được câu lệnh này.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Chọn gì đó đi chứ.");
            return true;
        }
        if (args[0].equalsIgnoreCase("setup")) {
            HashMap<String, Object> playerData = new HashMap<>();
            // Gets current time and put it into a string.
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);
            playerData.put("baseHealth", 100);
            playerData.put("registerDate", strDate);
            playerData.put("baseMana", 100);
            playerData.put("baseDefense", 100);
            PlayerStorage.getDatabase().set(((Player) sender).getUniqueId().toString(), playerData);
            PlayerStorage.save();
        } else {
            sender.sendMessage(ChatColor.RED + "Câu lệnh mà bạn đã nhập không tồn tại.");
        }
        return true;
    }
}

