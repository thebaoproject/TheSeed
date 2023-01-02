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
import ga.baoproject.theseed.effects.Healing;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class HealingCrystal extends SeedItem {
    public HealingCrystal() {
        super(Material.EMERALD);
        setID("sao:healing_crystal");
        setName("Healing Crystal");
        setAbilities(List.of(
                new Ability().setName(new Localized("Hồi máu", "plugin.itemDetails.HealingCrystal.ability"))
                        .setDescription(new Localized("&7Hồi &a20&7 HP trong 3 giây. Hiệu ứng này kéo dài 20 giây.", "plugin.itemDetails.HealingCrystal.description"
                        )).setUsage(ItemAbilityUseAction.RIGHT_CLICK).setCost(0).setCooldown(0)
        ));
        setRarity(Rarity.UNCOMMON);
    }

    @Override
    public void rightClickAction(PlayerInteractEvent e) {
        try {
            SeedPlayer p = SeedPlayer.fromPlayer(e.getPlayer());
            Healing h = new Healing();
            h.setDuration(20);
            p.addEffect(h);
        } catch (InvalidEntityData ignored) {
        }
    }
}
