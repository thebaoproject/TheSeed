package me.spike.blockartonline.commands;

import me.spike.blockartonline.ItemList;
import me.spike.blockartonline.abc.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

import static java.lang.Integer.parseInt;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class GiveItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!("gsi".equalsIgnoreCase(label))) { return true; }

        Logger l = getLogger();
        Weapon item = ItemList.get(args[0]);
        ItemStack itemToGive = item.getItem();
        boolean specifyAmount = false;
        try {
            itemToGive.setAmount(parseInt(args[args.length-1]));
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
