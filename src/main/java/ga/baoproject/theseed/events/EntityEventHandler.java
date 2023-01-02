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

package ga.baoproject.theseed.events;

import ga.baoproject.theseed.abc.SeedBoss;
import ga.baoproject.theseed.abc.SeedEntity;
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
            SeedEntity victim = SeedEntity.fromEntity(e.getEntity());
            if (EntityUtils.get(victim.getID()) instanceof SeedBoss b) {
                Bukkit.broadcast(Component.text(Utils.color("&6Một người chơi đã tiêu diệt boss &c" + b.getName() + "&6!")));
                b.onDeath(e);
                e.setCancelled(false);
            }
        } catch (InvalidEntityData | InvalidEntityID ignored) {
        }
    }

}
