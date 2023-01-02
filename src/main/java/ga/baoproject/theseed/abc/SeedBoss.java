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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public abstract class SeedBoss extends SeedMonster {

    private BossBar bossBar;

    public SeedBoss(EntityType b) {
        super(b);
    }

    @Override
    public void renderHealth() {
        setHealth((int) getBase().getHealth());
        getBossBar().setProgress(((float) getHealth() / getMaxHealth()));
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
        Objects.requireNonNull(((LivingEntity) getEntity()).getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(getMaxHealth());
        setBossBar(Bukkit.createBossBar(ChatColor.RED + getName(), BarColor.RED, BarStyle.SOLID));
        getBossBar().setProgress(1);
        return getBase();
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar b) {
        bossBar = b;
    }

    public void onDeath(@NotNull EntityDeathEvent e) {
    }
}
