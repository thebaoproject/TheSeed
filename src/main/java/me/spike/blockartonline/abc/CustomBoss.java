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

package me.spike.blockartonline.abc;

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


public class CustomBoss extends CustomMonster {

    private BossBar bossBar;

    public CustomBoss(EntityType b) {
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
