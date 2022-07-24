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

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class ObjectEraser extends Weapon {

    public ObjectEraser() {
        super(Material.GOLDEN_SWORD);
        super.setID("object_eraser");
        super.setName("Object Eraser");
        super.setDamage(999999999);
        super.setStrength(999999999);
        super.setAbilities(List.of(
                new Ability().setName("One-hit-kill").setDescription("" +
                        ChatColor.translateAlternateColorCodes(
                                '&', "&7Giết chết sinh vật chỉ với one hit."
                        )
                ).setUsage(ItemAbilityUseAction.NONE).setCost(30).setCooldown(3)
        ));
        super.setRarity(Rarity.HAX);
    }

    public void attackAction(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Damageable victim) {
            try {
                CustomEntity entity = CustomEntity.fromEntity(victim);
                entity.setHealth(0);
            } catch (InvalidEntityData ignored) {
            }
        }
    }
}
