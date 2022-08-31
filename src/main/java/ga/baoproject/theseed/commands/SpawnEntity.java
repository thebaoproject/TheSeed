/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.abc.CustomMonster;
import ga.baoproject.theseed.exceptions.InvalidEntityID;
import ga.baoproject.theseed.utils.EntityUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnEntity implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!("spe".equalsIgnoreCase(label))) {
            return true;
        }
        if (!(sender instanceof Player p)) {
            sender.sendMessage(ChatColor.RED + "No console!");
            return true;
        }
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Bạn phải nêu một lựa chọn!");
            return true;
        }

        CustomMonster entity;
        try {
            entity = (CustomMonster) EntityUtils.get(args[0]);
            entity.spawnAt(p.getLocation());
            sender.sendMessage(Component.text(ChatColor.GREEN + "Whoosh!"));
        } catch (InvalidEntityID e) {
            sender.sendMessage("Entity bạn lựa chọn không tồn tại.");
            return true;
        }
        return true;
    }
}
