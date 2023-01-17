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

package ga.baoproject.theseed.api.types;

import ga.baoproject.theseed.i18n.Localized;

/**
 * Represents an item's ability.
 */
public class SeedAbility {
    private SeedAbilityActivation usage;
    private Localized name;
    private Localized description;
    private int cost;
    private int cooldown;

    public Localized getDescription() {
        return this.description;
    }

    public SeedAbility setDescription(Localized description) {
        this.description = description;
        return this;
    }

    public Localized getName() {
        return name;
    }

    public SeedAbility setName(Localized name) {
        this.name = name;
        return this;
    }

    public SeedAbilityActivation getUsage() {
        return usage;
    }

    public SeedAbility setUsage(SeedAbilityActivation usage) {
        this.usage = usage;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public SeedAbility setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public int getCooldown() {
        return cooldown;
    }

    public SeedAbility setCooldown(int c) {
        this.cooldown = c;
        return this;
    }
}
