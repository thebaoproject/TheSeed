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

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.TheSeed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class SeedBoss extends SeedMonster {

    public SeedBoss(EntityType b) {
        super(b);
    }

    @Override
    public void renderHealth() {
        setHealth((int) getBase().getHealth());
        getBossBar().setProgress(((float) getHealth() / getMaxHealth()));
        DebugLogger.debug("Setting bossbar progress to " + (float) getHealth() / getMaxHealth());
    }

    /**
     * Spawns a boss at a specific location.
     *
     * @param l the location to spawn the boss,
     * @return the {@link Damageable} spawned.
     */
    @NotNull
    @Override
    public Damageable spawnAt(@NotNull Location l) {
        setBase((Damageable) l.getWorld().spawnEntity(l, getBaseType()));
        // Have to write the NBT to each entity spawn.
        getBase().setCustomNameVisible(true);
        setNameTag();
        setID(getID());
        setMaxHealth(getMaxHealth());
        setLevel(getLevel());
        setName(getName());
        setHealth(getHealth());
        setLastHealth(getLastHealth());
        setBaseHealth(getBaseHealth());
        Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH))
                .setBaseValue(getMaxHealth());
        // TODO - makes bossbars disappear when out of range
        DebugLogger.debug(
                "Setting bossbar: " + new NamespacedKey(TheSeed.getInstance(), getBase().getUniqueId().toString()));
        KeyedBossBar kb = Bukkit.createBossBar(
                new NamespacedKey(TheSeed.getInstance(), getBase().getUniqueId().toString()), ChatColor.RED + getName(),
                BarColor.RED, BarStyle.SOLID);
        for (Entity i : getBase().getNearbyEntities(100, 20, 100)) {
            if (i instanceof Player p)
                kb.addPlayer(p);
        }
        Objects.requireNonNull(getBossBar()).setProgress(1);
        return getBase();
    }

    @Nullable
    public BossBar getBossBar() {
        if (getBase() != null) {
            DebugLogger.debug(
                    "Getting bossbar: " + new NamespacedKey(TheSeed.getInstance(), getBase().getUniqueId().toString()));
            KeyedBossBar kb = Bukkit
                    .getBossBar(new NamespacedKey(TheSeed.getInstance(), getBase().getUniqueId().toString()));
            if (kb != null) {
                for (Entity i : getBase().getNearbyEntities(100, 20, 100)) {
                    if (i instanceof Player p)
                        kb.addPlayer(p);
                }
            }
            return kb;
        }
        return null;
    }

    public abstract void onDeath(@NotNull EntityDeathEvent e);

    public void removeBossBar(Damageable e) {
        KeyedBossBar kb = Bukkit.getBossBar(new NamespacedKey(TheSeed.getInstance(), e.getUniqueId().toString()));
        if (kb == null) {
            return;
        }
        kb.setVisible(false);
        kb.removeAll();
        Bukkit.removeBossBar(new NamespacedKey(TheSeed.getInstance(), e.getUniqueId().toString()));
    }
}
