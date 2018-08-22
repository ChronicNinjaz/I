package com.chronicninjazdevelopments.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Objects;

public class PacketUtil {

    public static Class<?> getNMSClass(String nmsClassString) {

        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
            String name = "net.minecraft.server." + version + nmsClassString;
            return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Class<?> getBukkitClass(String bukkitClassString) {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
            String name = "org.bukkit.craftbukkit." + version + bukkitClassString;
            return Class.forName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getNMSPlayer(Player player) {

        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            return getHandle.invoke(player);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getConnection(Player player) {

        try {
            Object nmsPlayer = getNMSPlayer(player);
            return Objects.requireNonNull(nmsPlayer).getClass().getField("playerConnection").get(nmsPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getNetworkManager(Player player) {

        try {
            return Objects.requireNonNull(getConnection(player)).getClass().getField("networkManager").get(getConnection(player));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
