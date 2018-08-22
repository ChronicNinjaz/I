package com.chronicninjazdevelopments.minions.types;

import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class Healer extends Minion {
    private int radius;

    public Healer(int id, String name, PlayerData owner, boolean spawned, ArrayList<UUID> trusted, int health, int hunger, int maxHealth, int maxHunger, SuitType suit, Location location, boolean boosted, MinionType minionType){
        super(id, name, owner, spawned, trusted, health, hunger, maxHealth, maxHunger, suit, location, boosted, minionType);

        radius = 4;

        if(boosted)
            radius = radius * 2;

        if(spawned)
            loadMinion(location);
    }

    public int getRadius() {
        return radius;
    }
}
