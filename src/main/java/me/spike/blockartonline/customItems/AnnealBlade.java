package me.spike.blockartonline.customItems;

import me.spike.blockartonline.abc.Ability;
import me.spike.blockartonline.abc.Weapon;
import me.spike.blockartonline.abc.ItemAbilityUseAction;
import me.spike.blockartonline.abc.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class AnnealBlade extends Weapon implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        long playerZ = Math.round(p.getLocation().getDirection().getZ());
        List<Entity> nearbyEntities = p.getNearbyEntities(5, 5, 5);
        // hey want math?
        for (Entity i : nearbyEntities) {
            long erz = Math.round(p.getLocation().toVector().add(i.getLocation().toVector().multiply(-1)).getZ());
            long erx = Math.round(p.getLocation().toVector().add(i.getLocation().toVector().multiply(-1)).getX());
            if (playerZ * erz == erx) {
                // Kill the entity if it is killable.
                if (i instanceof Damageable && !i.isInvulnerable()) {
                    ((Damageable) i).setHealth(0);
                }
            }
        }
        Vector direction = p.getLocation().getDirection();
        Vector dashMotion = new Vector(direction.getX(), 0, direction.getZ());
        p.setVelocity(dashMotion.multiply(5));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Bạn đã sử dụng khả năng đặc biệt của &dAnneal Blade&6!"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttack(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
        if (
            !e.getEntityType().equals(EntityType.DROPPED_ITEM) &&
            (e.getCause().equals(EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) || e.getCause().equals(EntityDamageByEntityEvent.DamageCause.ENTITY_SWEEP_ATTACK)) &&
            e.getDamager() instanceof Player && ((Player) e.getDamager()).getInventory().getItemInMainHand().equals(getItem()) &&
            e.getEntity() instanceof Damageable ne
        ) {
            if (e.getCause() == EntityDamageByEntityEvent.DamageCause.ENTITY_ATTACK) {
                ne.setHealth(ne.getHealth() + e.getFinalDamage() - calculateDamage());
                getLogger().log(Level.INFO, "Inflicted " + calculateDamage() + " on " + e.getEntity().getType().name());
            }
        }
    }

    public AnnealBlade() {
        setBaseItemType(new ItemStack(Material.STONE_SWORD, 1));
        setID("anneal_blade");
        setName("Anneal Blade");
        setDamage(35);
        setStrength(2);
        setAbilities(List.of(
                new Ability().setName("Phi đao").setDescription("" +
                        ChatColor.translateAlternateColorCodes(
                                '&', "&7Tiến thẳng tới trước &a5&7 block.\n"
                                        + "&7Gây ra &a" + getDamage() + "&7 sát thương cho bất kì\n"
                                        + "&7sinh vật nào trên đường đi."
                        )
                ).setUsage(ItemAbilityUseAction.RIGHT_CLICK)
        ));
        setRarity(Rarity.UNCOMMON_SWORD);
    }
}
