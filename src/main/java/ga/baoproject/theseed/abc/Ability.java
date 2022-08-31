/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.abc;

import ga.baoproject.theseed.i18n.Localized;

/**
 * Represents an item's ability.
 */
public class Ability {
    private ItemAbilityUseAction usage;
    private Localized name;
    private Localized description;
    private int cost;
    private int cooldown;

    public Localized getDescription() {
        return this.description;
    }

    public Ability setDescription(Localized description) {
        this.description = description;
        return this;
    }

    public Localized getName() {
        return name;
    }

    public Ability setName(Localized name) {
        this.name = name;
        return this;
    }

    public ItemAbilityUseAction getUsage() {
        return usage;
    }

    public Ability setUsage(ItemAbilityUseAction usage) {
        this.usage = usage;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public Ability setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public int getCooldown() {
        return cooldown;
    }

    public Ability setCooldown(int c) {
        this.cooldown = c;
        return this;
    }
}
