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

package ga.baoproject.theseed.game.effects;

import ga.baoproject.theseed.api.types.SeedEffect;
import ga.baoproject.theseed.api.types.SeedPlayer;

public class VanillaEffect extends SeedEffect {
    public VanillaEffect(String s) {
        setId(s);
    }


    @Override
    public void applyEffect(SeedPlayer p) {
    }
}