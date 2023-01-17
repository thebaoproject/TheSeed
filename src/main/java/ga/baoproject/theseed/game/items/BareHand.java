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

package ga.baoproject.theseed.game.items;

import ga.baoproject.theseed.api.types.SeedEntity;
import ga.baoproject.theseed.api.types.SeedWeapon;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Yes. A bare hand.
 */
public class BareHand extends SeedWeapon {
    public BareHand() {
        super(Material.AIR);
        super.setDamage(1);
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        int damage = (int) e.getFinalDamage();
        try {
            SeedEntity entity = SeedEntity.fromEntity((Damageable) e.getEntity());
            entity.setHealth(entity.getHealth() - damage);
        } catch (InvalidEntityData ignored) {
        }
    }
}
