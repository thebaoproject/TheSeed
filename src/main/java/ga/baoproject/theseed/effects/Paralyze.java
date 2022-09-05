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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * You got shot by Death Gun
 */
public class Paralyze extends CustomEffect {
    public Paralyze() {
        setName(new Localized("Tê liệt", "plugin.effect.paralyze.name"));
        setDescription(new Localized("Gây tê liệt toàn thân, làm cho người chơi không thể di chuyển.", "plugin.effect.paralyze.description"));
        setId("sao:paralyze");
    }

    @Override
    public void applyEffect(CustomPlayer p) {
        p.getBase().addPotionEffect(
                // Maximum slowness
                new PotionEffect(PotionEffectType.SLOW, 30, 255)
        );
    }
}
