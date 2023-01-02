/*
 * Copyright 2022-2023 SpikeBonjour
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ga.baoproject.theseed.commands;

import ga.baoproject.theseed.abc.SeedMonster;
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

        SeedMonster entity;
        try {
            entity = (SeedMonster) EntityUtils.get(args[0]);
            entity.spawnAt(p.getLocation());
            sender.sendMessage(Component.text(ChatColor.GREEN + "Whoosh!"));
        } catch (InvalidEntityID e) {
            sender.sendMessage("Entity bạn lựa chọn không tồn tại.");
            return true;
        }
        return true;
    }
}
