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

package ga.baoproject.theseed.monsters;

import ga.baoproject.theseed.abc.CustomBoss;
import ga.baoproject.theseed.abc.DebugLogger;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class IllfangBoss extends CustomBoss {
    public IllfangBoss() {
        super(EntityType.WARDEN);
        DebugLogger.debug("Trying to spawn an Illfang the Kobold Lord...");
        setLevel(30);
        setID("boss_illfang");
        setName("Illfang the Kobold Lord");
        setMaxHealth(1000);
        setHealth(1000);
        setLastHealth(1000);
    }

    public void onDeath(@NotNull EntityDeathEvent e) {
        DebugLogger.debug("Got death event");
        e.setDeathSound(Sound.ENTITY_ENDER_DRAGON_DEATH);
        e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.DIRT));
        getBossBar().removeAll();
    }

}
