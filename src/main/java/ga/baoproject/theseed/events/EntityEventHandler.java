/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.events;

import ga.baoproject.theseed.abc.CustomBoss;
import ga.baoproject.theseed.abc.CustomEntity;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.exceptions.InvalidEntityID;
import ga.baoproject.theseed.utils.EntityUtils;
import ga.baoproject.theseed.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityEventHandler {

    public static void onDeath(EntityDeathEvent e) {
        try {
            CustomEntity victim = CustomEntity.fromEntity(e.getEntity());
            if (EntityUtils.get(victim.getID()) instanceof CustomBoss b) {
                Bukkit.broadcast(Component.text(Utils.color("&6Một người chơi đã tiêu diệt boss &c" + b.getName() + "&6!")));
                b.onDeath(e);
                e.setCancelled(false);
            }
        } catch (InvalidEntityData | InvalidEntityID ignored) {
        }
    }

}
