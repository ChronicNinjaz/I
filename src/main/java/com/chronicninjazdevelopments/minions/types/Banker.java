package com.chronicninjazdevelopments.minions.types;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Banker extends Minion {
    private static int[] cost = { 50000, 100000, 200000, 600000, 1000000 };
    private HashMap<Material, Double> items;
    private int radius;
    private double balance;
    private double maxBalance;
    private int level;

    public Banker(int id, String name, PlayerData owner, boolean spawned, ArrayList<UUID> trusted, int health, int hunger, int maxHealth, int maxHunger, SuitType suit, Location location, boolean boosted, int level, double balance){
        super(id, name, owner, spawned, trusted, health, hunger, maxHealth, maxHunger, suit, location, boosted, MinionType.BANKER);

        this.maxBalance = 250000 * level;
        this.radius = Minions.getInstance().getConfiguration().getInt("settings.BANKER.sellRadius");
        this.level = level;
        this.balance = balance;
        this.items = new HashMap<>();

        for(String key : Minions.getInstance().getConfiguration().getConfigurationSection("settings.BANKER.prices").getKeys(false)){
            this.items.put(Material.getMaterial(key), Minions.getInstance().getConfiguration().getDouble("settings.BANKER.prices." + key));
        }

        if(spawned)
            loadMinion(location);
    }

    public static int[] getCost() {
        return cost;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(double maxBalance) {
        this.maxBalance = maxBalance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public HashMap<Material, Double> getItems() {
        return items;
    }
}
