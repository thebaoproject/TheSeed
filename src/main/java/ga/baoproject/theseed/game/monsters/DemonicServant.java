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

package ga.baoproject.theseed.game.monsters;

import ga.baoproject.theseed.api.types.SeedMonster;
import org.bukkit.entity.EntityType;

public class DemonicServant extends SeedMonster {
    public DemonicServant() {
        super(EntityType.WITHER_SKELETON);
        setLevel(10);
        setID("sao:demonic_servant");
        setName("Demonic Servant");
        setMaxHealth(100);
        setHealth(100);
        setLastHealth(100);
    }
}
