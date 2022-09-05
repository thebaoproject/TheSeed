/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.effects;

import ga.baoproject.theseed.abc.CustomEffect;
import ga.baoproject.theseed.abc.CustomPlayer;

public class VanillaEffect extends CustomEffect {
    public VanillaEffect(String s) {
        setId(s);
    }


    @Override
    public void applyEffect(CustomPlayer p) {
    }
}
