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

public class Healing extends SeedEffect {
    public Healing() {
        setName(new Localized("Hồi máu", "plugin.effect.healing.name"));
        setDescription(new Localized("Hồi máu người chơi.", "plugin.effect.healing.description"));
        setId("sao:healing");
    }

    @Override
    public void applyEffect(SeedPlayer p) {
        p.setHealth(p.getHealth() + 6);
    }
}
