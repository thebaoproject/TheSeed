/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.armor;

import ga.baoproject.theseed.abc.BuffTrigger;
import ga.baoproject.theseed.abc.CustomTalisman;
import ga.baoproject.theseed.abc.Rarity;
import org.bukkit.Material;

public class CoatOfMidnight extends CustomTalisman {
    public CoatOfMidnight() {
        super(Material.LEATHER_CHESTPLATE);
        setID("sao:coat_of_midnight");
        setProtection(50);
        setKnockbackMitigation(10);
        setHealthBuff(10);
        setSpeedBuff(10);
        setName("Coat of Midnight");
        setRarity(Rarity.RARE);
        setTrigger(BuffTrigger.WEARING);
    }
}
