package com.chronicninjazdevelopments.backpack;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Banker;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.minions.types.Miner;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Backpack {
    private int maximumAllowed;
    private ArrayList<Minion> minions;

    public Backpack(PlayerData playerData, FileConfiguration config) {
        this.minions        = new ArrayList<>();

        /*if(Minions.getInstance().getMinionManager()!=null&&Minions.getInstance().getMinionManager().getCustomSkyBlock() != null && Minions.getInstance().getMinionManager().getCustomSkyBlock().isEnabled()){
            setMaximumAllowed(Minions.getInstance().getMinionManager().getCustomSkyBlock().getMinionWrapper().getUser(playerData.getPlayer().getUniqueId()).getBackpackLimit());
            return;
        }
        */

        setMaximumAllowed(config.getInt("backpackLimit"));

        if(config.isSet("Minions")){
            for(String key : config.getConfigurationSection("Minions").getKeys(false)){
                String path = "Minions." + key + ".";

                int id = Integer.parseInt(key);
                String name = config.getString(path + "name");
                int hunger = config.getInt(path + "hunger");
                int health = config.getInt(path + "health");
                int maxHealth = config.getInt(path + "maxHealth");
                int maxHunger = config.getInt(path + "maxHunger");
                SuitType suit = SuitType.valueOf(config.getString(path + "suit"));

                boolean spawned = config.getBoolean(path + "spawned");
                boolean boosted = config.getBoolean(path + "boosted");

                List<String> trustedMembers = config.getStringList(path + "trustedMembers");
                ArrayList<UUID> finalTrustedMembers = new ArrayList<>();
                for (String member : trustedMembers)
                    finalTrustedMembers.add(UUID.fromString(member));

                Location location = null;
                if(spawned)
                    location = LocationUtils.decodeFullLocation(config.getString(path + "location"));

                MinionType type = MinionType.valueOf(config.getString(path + "type"));

                switch (type){
                    case MINER:
                        int blocksMined = config.getInt(path + "blocksMined");

                        Location chestLocation = null;
                        if(!config.getString(path + "linkedChest").equalsIgnoreCase("null"))
                            chestLocation = LocationUtils.decodeFullLocation(config.getString(path + "linkedChest"));

                        Miner miner = new Miner(id, name, playerData, spawned, finalTrustedMembers, health, hunger, maxHealth, maxHunger, suit, location, boosted, blocksMined, chestLocation);
                        this.minions.add(miner);
                        break;

                    case BUTCHER:
                        int level = config.getInt(path + "level");
                        int exp = config.getInt(path + "exp");
                        int radius = config.getInt(path + "radius");
                        EntityType target = EntityType.valueOf(config.getString(path + "target"));

                        Butcher butcher = new Butcher(id, name, playerData, spawned, finalTrustedMembers, health, hunger, maxHealth, maxHunger, suit, location, boosted, exp, level, radius, target);
                        this.minions.add(butcher);
                        break;

                    case BANKER:
                        int banker_level = config.getInt(path + "level");
                        int banker_balance = config.getInt(path + "balance");
                        Banker banker = new Banker(id, name, playerData, spawned, finalTrustedMembers, health, hunger, maxHealth, maxHunger, suit, location, boosted, banker_level, banker_balance);
                        this.minions.add(banker);
                }
            }
        }
    }

    public int getMaximumAllowed() {
        return maximumAllowed;
    }

    public void setMaximumAllowed(int maximumAllowed) {
        this.maximumAllowed = maximumAllowed;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }
}
