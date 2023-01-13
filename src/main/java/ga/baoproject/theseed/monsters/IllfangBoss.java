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

package ga.baoproject.theseed.monsters;

import ga.baoproject.theseed.abc.DebugLogger;
import ga.baoproject.theseed.abc.SeedBoss;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IllfangBoss extends SeedBoss {
    public IllfangBoss() {
        super(EntityType.WARDEN);
        DebugLogger.debug("Trying to spawn an Illfang the Kobold Lord...");
        setLevel(30);
        setID("sao:illfang_boss");
        setName("Illfang the Kobold Lord");
        setBaseHealth(1000);
        setMaxHealth(1000);
        setHealth(1000);
        setLastHealth(1000);
    }

    public void onDeath(@NotNull EntityDeathEvent e) {
        DebugLogger.debug("Got death event");
        e.setDeathSound(Sound.ENTITY_ENDER_DRAGON_DEATH);
        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.DIRT));
        removeBossBar(e.getEntity());
    }

}
