/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law. You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.monsters;

import ga.baoproject.theseed.abc.CustomMonster;
import org.bukkit.entity.EntityType;

public class DemonicServant extends CustomMonster {
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
