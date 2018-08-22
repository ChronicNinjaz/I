package com.chronicninjazdevelopments.minions.types;

import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.ExperienceManager;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.UUID;

public class Butcher extends Minion {
    public static int[] butcherLevels = {20000, 50000, 120000, 500000, 1000000};

    private int level;
    private int exp;
    private int maximumExp;
    private int radius;
    private EntityType target;

    public Butcher(int id, String name, PlayerData owner, boolean spawned, ArrayList<UUID> trusted, int health, int hunger, int maxHealth, int maxHunger, SuitType suit, Location location, boolean boosted, int exp, int level, int radius, EntityType selectedTarget) {
        super(id, name, owner, spawned, trusted, health, hunger, maxHealth, maxHunger, suit, location, boosted, MinionType.BUTCHER);
        this.level = level;
        this.maximumExp = 50 * level;
        this.radius = radius;
        this.exp = exp;
        this.target = selectedTarget;

        if(spawned)
            loadMinion(location);
    }

    public Butcher(Minion minion, PlayerData data) {
            super(minion.getId(),minion.getName(),data,minion.isSpawned(),minion.getTrustedMembers(),minion.getHealth(),minion.getHunger(),minion.getMaxHealth(),minion.getMaxHunger(),minion.getSuit().getSuitType(),minion.getLocation(),minion.isBoosted(),minion.getMinionType());
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setMaximumExp(int maximumExp) {
        this.maximumExp = maximumExp;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTarget(EntityType target) {
        this.target = target;
    }

    public void withdrawExp(){
        if(exp > 0) {
            ExperienceManager.addTotalExperience(getOwner(), exp);
            this.exp = 0;
            getOwner().sendMessage("You have withdrawn your exp!");
        }
    }

    public void addExp(int amount){
        if(exp+amount > maximumExp){
            this.exp = maximumExp;
            return;
        }
        this.exp += amount;
    }

    public void upgrade(){
        this.level++;
    }

    public void changeTargetType(EntityType entityType){
        this.target = entityType;
    }

    public int getLevel(){
        return level;
    }

    public int getRadius(){
        return radius;
    }

    public EntityType getTarget() {
        return target;
    }

    public int getExp(){
        return exp;
    }

}
