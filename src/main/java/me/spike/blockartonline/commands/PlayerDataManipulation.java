package me.spike.blockartonline.commands;

import me.spike.blockartonline.PlayerStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PlayerDataManipulation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

