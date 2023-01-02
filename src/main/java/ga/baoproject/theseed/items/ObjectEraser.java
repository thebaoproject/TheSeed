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

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class ObjectEraser extends SeedWeapon {

    public ObjectEraser() {
        super(Material.GOLDEN_SWORD);
        setID("sao:object_eraser");
        setName("Object Eraser");
        setDamage(999999999);
        setStrength(999999999);
        setAbilities(List.of(
                new Ability()
                        .setName(new Localized("Phá hủy", "plugin.itemDetails.ObjectEraser.ability"))
                        .setDescription(new Localized(
                                "&7Giết chết bất ki sinh vật nào chỉ với one hit.",
                                "plugin.itemDetails.ObjectEraser.description"
                        )).setUsage(ItemAbilityUseAction.NONE).setCost(0).setCooldown(0)
        ));
        setRarity(Rarity.HAX);
    }

    public void attackAction(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Damageable victim) {
            try {
                SeedEntity entity = SeedEntity.fromEntity(victim);
                entity.setHealth(0);
            } catch (InvalidEntityData ignored) {
            }
        }
    }
}
