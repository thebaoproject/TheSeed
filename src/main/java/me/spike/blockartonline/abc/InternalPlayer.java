package me.spike.blockartonline.abc;

import org.bukkit.entity.Player;

public class InternalPlayer {
    private Player player;
    private int maxHealth;
    private int maxMana;
    private int baseDefense;

    public Player getPlayer() { return player; }
    public void setPlayer(Player p) { player = p; }

    public int getMaxHealth() { return maxHealth; }
    public void setMaxHealth(int h) { maxHealth = h; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int m) { maxMana = m; }

    public int getBaseDefense() { return baseDefense; }
    public void setBaseDefense(int d) { baseDefense = d; }
}