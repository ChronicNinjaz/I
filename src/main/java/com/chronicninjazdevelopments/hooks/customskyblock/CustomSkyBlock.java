package com.chronicninjazdevelopments.hooks.customskyblock;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.hooks.customskyblock.database.MinionWrapper;
import com.chronicninjazdevelopments.hooks.customskyblock.database.SkyblockPlayerWrapper;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Optional;

public class CustomSkyBlock implements Listener {
    /*
    private com.cloudescape.database.Mongo mongo;
    private boolean enabled;

    private SkyblockPlayerWrapper skyblockPlayerWrapper;
    private MinionWrapper minionWrapper;

    public CustomSkyBlock() {
        if (!Minions.getInstance().getServer().getPluginManager().isPluginEnabled("CloudSkyblock")) {
            System.out.println("Failed to enable hook for CloudSkyblock.");
            this.enabled = false;
            return;
        }

       try {
           this.mongo = CloudSkyblock.getPlugin().getMongo();
           this.mongo.registerWrapper(MinionWrapper.COLLECTION_NAME, this.minionWrapper = new MinionWrapper());
           this.mongo.registerWrapper(SkyblockPlayerWrapper.COLLECTION_NAME, this.skyblockPlayerWrapper = new SkyblockPlayerWrapper());

       } catch (Exception ignored) {
           System.out.println("Failed to enable hook for CloudSkyblock.");
           this.enabled = false;
           return;
       }
        Bukkit.getPluginManager().registerEvents(this, Minions.getInstance());
        this.enabled = true;
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
        System.out.println("=========================");
    }

    @EventHandler(ignoreCancelled = true)
    public void onIslandPreUnloadEvent(IslandPreUnloadEvent event) {
        event.getIsland().getIslandMembers().forEach((uuid, rank) -> {
            Player player = Bukkit.getPlayer(uuid);
            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(player);
            if (playerData != null) {
                if (!playerData.getBackpack().getMinions().isEmpty()) {
                    playerData.getBackpack().getMinions().forEach(minion -> {
                        Minions.getInstance().getMinionManager().saveMinion(minion);
                        if (minion.isSpawned())
                            minion.getMinion().remove();

                    });
                }
            }
        });
    }

    public boolean hasPermissionToBuild(Player player) {
        // Api api = SkyBlockApi.getApi(); not done... nice | need to check if the player is allowed to place at that location

        // Attempt to handle worldguard here if you need to..

        if (this.enabled) {
            Optional<Island> islandOptional = CloudSkyblock.getPlugin().getIslandManager().getIslandByWorld(player.getWorld());

            if (!islandOptional.isPresent()) return false;

            Island island = islandOptional.get();

            return island.getIslandMembers().containsKey(player.getUniqueId());
        }

        return true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onIslandLoadEvent(IslandLoadingEvent event) {
        if (!this.enabled)
            return;

        /**
         * Load minions in to world
         */

    /**
        boolean loadedSuccessfully = event.getIsland().getWorld() != null;
        Island island = event.getIsland();

        if (!loadedSuccessfully || island == null) {
            System.out.println("There were issues spawning minions on (" + event.getIslandContainer().getOwner() + ")'s island!");
            return;
        }

        island.getIslandMembers().forEach((uuid, rank) -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                PlayerData data = Minions.getInstance().getMinionManager().getPlayerData(player);

                if (data != null) {
                    if (!data.getBackpack().getMinions().isEmpty()) {
                        data.getBackpack().getMinions().forEach(minion -> {
                            if (minion.isSpawned()) minion.loadMinion(minion.getLocation());
                        });
                    }
                }
            }
        });
    }

    /**
     * Check to see if the island is already loaded
     * Can run this onJoin, if returned false add to a arrayList
     * and wait for island to load to check arrayList to load islands
     */

    /**
    public boolean canSpawnPlayersMinion(Player player) {
        return SkyBlockApi.getApi().isIslandLoaded(player);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public SkyblockPlayerWrapper getSkyblockPlayerWrapper() {
        return skyblockPlayerWrapper;
    }

    public MinionWrapper getMinionWrapper() {
        return minionWrapper;
    }

    public com.cloudescape.database.Mongo getMongo() {
        return mongo;
    }

     */


}
