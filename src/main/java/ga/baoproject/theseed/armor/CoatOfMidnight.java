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

package ga.baoproject.theseed.armor;

import ga.baoproject.theseed.abc.SeedArmor;
import ga.baoproject.theseed.abc.Rarity;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

public class CoatOfMidnight extends SeedArmor {
    public CoatOfMidnight() {
        super(Material.LEATHER_CHESTPLATE);
        setID("sao:coat_of_midnight");
        setProtection(50);
        setKnockbackMitigation(10);
        setHealthBuff(10);
        setSpeedBuff(10);
        setName("Coat of Midnight");
        setRarity(Rarity.RARE);
        setTrigger(EquipmentSlot.CHEST);
    }
}
