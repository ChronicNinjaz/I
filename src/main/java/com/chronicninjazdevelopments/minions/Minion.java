package com.chronicninjazdevelopments.minions;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.animations.*;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.types.Banker;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.minions.types.Healer;
import com.chronicninjazdevelopments.minions.types.Miner;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.suits.type.Default;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class Minion {
    private int id;
    private String name;
    private ArmorStand minion;
    private ArrayList<UUID> trustedMembers;
    private Player owner;
    private boolean spawned;
    private int health, maxHealth;
    private int hunger, maxHunger;
    private ItemStack handItem;
    private Suit suit;
    private boolean boosted;
    private Location location;
    private Animation animation;
    private MinionType minionType;
    private PlayerData playerData;

    public Minion(int id, String name, PlayerData owner, boolean spawned, ArrayList<UUID> trusted, int health, int hunger, int maxHealth, int maxHunger, SuitType suit, Location location, boolean boosted, MinionType minionType) {
        this.id = id;
        this.name = name;
        this.owner = owner.getPlayer();
        this.playerData = owner;
        this.spawned = spawned;
        this.trustedMembers = trusted;
        this.health = health;
        this.hunger = hunger;
        this.maxHealth = maxHealth;
        this.maxHunger = maxHunger;
        this.boosted = boosted;
        this.location = location;
        this.minionType = minionType;

        this.suit = suit == null ? new Default(this) : suit.newSuit(this);
    }

    public void loadMinion(Location location){
        if(!spawned)
            spawned = true;

        minion = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);

        minion.setCustomNameVisible(true);
        minion.setSmall(true);
        minion.setRemoveWhenFarAway(false);
        minion.setVisible(true);
        minion.setBasePlate(false);
        minion.setCanPickupItems(false);
        minion.setArms(true);

        minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName())));
        suit.activate();

        switch (minionType) {
            case MINER:
                minion.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
                setAnimation(new MineBlockAnimation((Miner) this));
                animation.activate(0, 2);
                break;
            case HEALER:
                minion.setItemInHand(new ItemStack(Material.SPLASH_POTION));
                setAnimation(new HealerAnimation((Healer) this));
                animation.activate(0, (60 * 20) * 2);
                break;
            case BUTCHER:
                minion.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
                setAnimation(new ButcherAnimation((Butcher) this));
                animation.activate(0, 20 * 5);
                break;
            case BANKER:
                minion.setItemInHand(new ItemStack(Material.DIAMOND));
                setAnimation(new BankerAnimation((Banker) this));
                animation.activate(0, 20 * 20);
                break;
        }

        this.location = minion.getLocation();
    }

    public Block getFacingBlock() {
        double dir = (this.minion.getLocation().getYaw() * 4.0F / 360.0F) + 0.5D;
        int direction = (int) dir;
        switch (direction) {
            case 0:
                return minion.getLocation().clone().add(0, 0, 1).getBlock();
            case 1:
                return minion.getLocation().clone().add(-1, 0, 0).getBlock();
            case 2:
                return minion.getLocation().clone().add(0, 0, -1).getBlock();
            case 3:
                return minion.getLocation().clone().add(1, 0, 0).getBlock();
            default:
                return null;
        }
    }

    public void kill(){
        try {
            playerData.getConfig().set("minions." + id, null);
            playerData.getConfig().save(playerData.getFile());
        }catch (IOException e){e.printStackTrace();}
        playerData.getBackpack().getMinions().remove(this);
        if(spawned) {
            minion.remove();
            animation.stop();
        }
        owner.sendMessage("[!] Your minion has died due to no health!");
    }

    public void heal(int value){
        if(health + value > maxHealth){
            health = maxHealth;
            return;
        }
        health += value;

        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public void feed(int value){
        if(hunger + value > maxHunger){
            hunger = maxHunger;
            return;
        }
        hunger += value;

        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public void setMinion(ArmorStand minion) {
        this.minion = minion;
    }

    public void setTrustedMembers(ArrayList<UUID> trustedMembers) {
        this.trustedMembers = trustedMembers;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public int getMaxHunger() {
        return maxHunger;
    }

    public void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;
        if(spawned)
            minion.setCustomName(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("settings.MinionSettings.nameFormat").replace("%minion_name%", name).replace("%health%", String.valueOf(health)).replace("%maximum_health%", String.valueOf(maxHealth)).replace("%player%", playerData.getPlayer().getName()).replace("%minion_type%", minionType.name())));
    }

    public ItemStack getHandItem() {
        return handItem;
    }

    public void setHandItem(ItemStack handItem) {
        this.handItem = handItem;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public ArmorStand getMinion() {
        return minion;
    }

    public ArrayList<UUID> getTrustedMembers() {
        return trustedMembers;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public Location getLocation() {
        return location;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public MinionType getMinionType() {
        return minionType;
    }

    public void setMinionType(MinionType minionType) {
        this.minionType = minionType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
