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

package ga.baoproject.theseed.effects;


import ga.baoproject.theseed.abc.SeedEffect;
import ga.baoproject.theseed.abc.SeedPlayer;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * You got shot by Death Gun
 */
public class Paralyze extends SeedEffect {
    public Paralyze() {
        setName(new Localized("Tê liệt", "plugin.effect.paralyze.name"));
        setDescription(new Localized("Gây tê liệt toàn thân, làm cho người chơi không thể di chuyển.", "plugin.effect.paralyze.description"));
        setId("sao:paralyze");
    }

    @Override
    public void applyEffect(SeedPlayer p) {
        p.getBase().addPotionEffect(
                // Maximum slowness
                new PotionEffect(PotionEffectType.SLOW, 30, 255)
        );
    }
}
