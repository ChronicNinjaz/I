package com.chronicninjazdevelopments.minions.types;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Miner extends Minion {
    private int blocksMined;
    private Chest linkedChest;
    private ArrayList<String> blackList;

    public Miner(int id, String name, PlayerData owner, boolean spawned, ArrayList<UUID> trusted, int health, int hunger, int maxHealth, int maxHunger, SuitType suit, Location location, boolean boosted, int blocksMined, Location linkedChest){
        super(id, name, owner, spawned, trusted, health, hunger, maxHealth, maxHunger, suit, location, boosted, MinionType.MINER);

        this.blackList = (ArrayList<String>) Minions.getInstance().getConfiguration().getStringList("settings.MINER.blockBlackList");
        this.blocksMined = blocksMined;

        if(linkedChest != null && linkedChest.getBlock().getState() instanceof Chest)
            this.linkedChest = (Chest) linkedChest.getBlock().getState();

        if(spawned)
            loadMinion(location);
    }

    public Miner(PlayerData playerData, Minion minion) {
        super(minion.getId(),minion.getName(),playerData,minion.isSpawned(),minion.getTrustedMembers(),minion.getHealth(),minion.getHunger(),minion.getMaxHealth(),minion.getMaxHunger(),minion.getSuit().getSuitType(),minion.getLocation(),minion.isBoosted(),minion.getMinionType());
    }

    public ItemStack getOre(Material type){
        switch (type){
            case DIAMOND_ORE:   return new ItemStack(Material.DIAMOND);
            case IRON_ORE:      return new ItemStack(Material.IRON_INGOT);
            case GOLD_ORE:      return new ItemStack(Material.GOLD_INGOT);
            case COAL_ORE:      return new ItemStack(Material.COAL);
            case EMERALD_ORE:   return new ItemStack(Material.EMERALD);
            case REDSTONE_ORE:  return new ItemStack(Material.REDSTONE, new Random().nextInt(3) + 1);
            case GLOWING_REDSTONE_ORE: new ItemStack(Material.REDSTONE, new Random().nextInt(3) + 1);
            case LAPIS_ORE:     return new ItemStack(Material.INK_SACK, 1, (byte) 4);
            default: return new ItemStack(type);
        }
    }

    public int getBlocksMined() {
        return blocksMined;
    }

    public void setBlocksMined(int blocksMined) {
        this.blocksMined = blocksMined;
    }

    public Chest getLinkedChest() {
        return linkedChest;
    }

    public void setLinkedChest(Chest linkedChest) {
        this.linkedChest = linkedChest;
    }

    public ArrayList<String> getBlackList() {
        return blackList;
    }
}
