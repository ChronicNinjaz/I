package com.chronicninjazdevelopments.utils.interceptor.version.providers;

import com.chronicninjazdevelopments.utils.ReflectionUtils;
import com.chronicninjazdevelopments.utils.interceptor.PacketType;
import com.chronicninjazdevelopments.utils.interceptor.version.Version;
import com.chronicninjazdevelopments.utils.interceptor.version.VersionProvider;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Created by Matthew Eisenberg on 5/24/2018 at 9:23 PM for the project Minions
 */
public class VersionProviderV1_12 extends VersionProvider {
    public VersionProviderV1_12() {
        super(Version.V1_12);
    }
    @Override
    public String[] getSignLines(Object[] lines) {
        return Arrays.stream(lines).map(Object::toString).toArray(String[]::new);
    }

    @Override
    public Object setEditable(Sign sign, Player player) {
        try {
            Block signBlock = sign.getBlock();
            Object craftWorldObject = ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftWorld").cast(player.getWorld());
            Class<?> craftWorld = craftWorldObject.getClass();

            // WorldServer objects
            Object worldServerObject = craftWorld.getMethod("getHandle").invoke(craftWorldObject);
            Class<?> worldServer = worldServerObject.getClass();

            // Block position handling
            Class<?> blockPosition = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("BlockPosition");
            Object blockPositionObject = blockPosition.getConstructor(double.class, double.class, double.class).newInstance(signBlock.getLocation().getX(), signBlock.getLocation().getY(), signBlock.getLocation().getZ());

            // Tile entity handling
            Object tileEntitySignObject = ReflectionUtils.getMethod(worldServer, "getTileEntity", blockPosition).invoke(worldServerObject, blockPositionObject);
            Class<?> tileEntitySign = tileEntitySignObject.getClass();
            ReflectionUtils.setValue(tileEntitySignObject, tileEntitySign, true, "isEditable", true);


            // Attempt to make the TileEntity editable.

            // Packet data and sending.
            Object packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenSignEditor").getConstructor(blockPosition).newInstance(blockPositionObject);
            return packet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PacketType getSignPacketType() {
        return PacketType.PACKET_PLAY_IN_UPDATE_SIGN;
    }
}
