package com.chronicninjazdevelopments.hooks.worldguard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardHook {
    private boolean enabled;
    private WorldGuardPlugin worldGuardPlugin;

    public WorldGuardHook(){
        if(Bukkit.getPluginManager().getPlugin("WorldGuard") != null){
            System.out.println("Failed to enable hook for World Guard.");
            this.enabled = false;
            return;
        }

        this.enabled = true;
        this.worldGuardPlugin = WorldGuardPlugin.inst();
    }

    public boolean canBuild(Player player, Location location){
        return worldGuardPlugin.canBuild(player, location);
    }

    public boolean isEnabled() {
        return enabled;
    }
}
