package me.spike.blockartonline.abc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemEventHandler implements Listener {

    @EventHandler(priority=EventPriority.HIGH)
    public static void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
    }

}
