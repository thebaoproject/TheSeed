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
import ga.baoproject.theseed.i18n.Localized;

public class Healing extends CustomEffect {
    public Healing() {
        setName(new Localized("Hồi máu", "plugin.effect.healing.name"));
        setDescription(new Localized("Hồi máu người chơi.", "plugin.effect.healing.description"));
        setId("sao:healing");
    }

    @Override
    public void applyEffect(CustomPlayer p) {
        p.setHealth(p.getHealth() + 6);
    }
}
