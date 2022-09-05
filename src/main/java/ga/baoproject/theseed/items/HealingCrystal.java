/*
 * Copyright (c) 2022 the Block Art Online Project contributors.
 *
 * This work is free. It comes without any warranty, to the extent permitted
 * by applicable law.You can redistribute it and/or modify it under the terms
 * of the Do What The Fuck You Want To Public License, Version 2.
 * See the LICENSE file for more details.
 */

package ga.baoproject.theseed.items;

import ga.baoproject.theseed.abc.*;
import ga.baoproject.theseed.effects.Healing;
import ga.baoproject.theseed.exceptions.InvalidEntityData;
import ga.baoproject.theseed.i18n.Localized;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class HealingCrystal extends CustomItem {
    public HealingCrystal() {
        super(Material.EMERALD);
        setID("sao:healing_crystal");
        setName("Healing Crystal");
        setAbilities(List.of(
                new Ability().setName(new Localized("Hồi máu", "plugin.itemDetails.HealingCrystal.ability"))
                        .setDescription(new Localized("&7Hồi &a20&7 HP trong 3 giây. Hiệu ứng này kéo dài 20 giây.", "plugin.itemDetails.HealingCrystal.description"
                        )).setUsage(ItemAbilityUseAction.RIGHT_CLICK).setCost(0).setCooldown(0)
        ));
        setRarity(Rarity.UNCOMMON);
    }

    @Override
    public void rightClickAction(PlayerInteractEvent e) {
        try {
            CustomPlayer p = CustomPlayer.fromPlayer(e.getPlayer());
            Healing h = new Healing();
            h.setDuration(20);
            p.addEffect(h);
        } catch (InvalidEntityData ignored) {
        }
    }
}
