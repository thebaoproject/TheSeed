/*
 * Copyright (c) 2022 SpikeBonjour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.spike.blockartonline.commands;

import me.spike.blockartonline.abc.CustomMonster;
import me.spike.blockartonline.exceptions.UnknownEntity;
import me.spike.blockartonline.utils.EntityUtils;
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
        } catch (UnknownEntity e) {
            sender.sendMessage("Entity bạn lựa chọn không tồn tại.");
            return true;
        }
        return true;
    }
}
