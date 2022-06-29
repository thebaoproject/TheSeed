package me.spike.blockartonline.events;

import me.spike.blockartonline.BlockArtOnline;
import me.spike.blockartonline.abc.DebugLogger;
import me.spike.blockartonline.abc.InternalPlayer;
import me.spike.blockartonline.exceptions.InvalidPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.jetbrains.annotations.NotNull;


/**
 * Listen to player events
 */
public class PlayerEventHandler {

    /**
     * Lets the plugin handles the damage because it's health system is separated from Minecraft.
     */
    public static void onEntityDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        try {
            InternalPlayer p = InternalPlayer.fromPlayer((Player) event.getEntity());
            // TODO - Custom armor and potions
            if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
                // Damage inflation, normal health is now 100, not 20.
                p.setHealth(p.getHealth() - (int) event.getFinalDamage()*5);
            } else {
                // Let x be our original damage.
                // x = health/maxHealth * 20 <-> health/maxHealth = x/20 <-> 20*health = maxHealth*x <-> health = maxHealth*x/20
                p.setHealth(p.getHealth() - (int) event.getFinalDamage() * p.getMaxHealth() / 20);
            }
            DebugLogger.debug("Setting " + p.getPlayer().getName() + "'s health to " + (p.getHealth() - (int) event.getFinalDamage()));
            event.setCancelled(true);
        } catch (InvalidPlayerData e) {
            if (((Player) event.getEntity()).getHealth() != 0) {
                BlockArtOnline.getInstance().getSLF4JLogger().error("Received InvalidPlayerData in the player event listener. THIS SHOULDN'T BE POSSIBLE. Silently ignoring...");
            }
        }
    }


    /**
     * Disables natural regen for players, regeneration is managed by the plugin.
     */
    public static void onEntityRegen(@NotNull EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

}
