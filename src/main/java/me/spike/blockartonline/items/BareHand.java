package me.spike.blockartonline.items;

import me.spike.blockartonline.abc.Weapon;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Yes. A bare hand.
 */
public class BareHand extends Weapon {
    public BareHand() {
        super(Material.AIR);
        super.setDamage(1);
    }

    @Override
    public void attackAction(EntityDamageByEntityEvent e) {
        super.attackAction(e);
    }
}
