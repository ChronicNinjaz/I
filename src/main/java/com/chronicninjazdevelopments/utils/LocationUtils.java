package com.chronicninjazdevelopments.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    public static String convertFullLocation(Location location){
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
    }

    public static Location decodeFullLocation(String string){
        if(string == "null"){
            return null;
        }

        String args[] = string.split(":");

        if(args.length < 6){
            return null;
        }

        World world = Bukkit.getWorld(args[0]);

        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);

        float yaw = Float.valueOf(args[4]);
        float pitch = Float.valueOf(args[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }


}
