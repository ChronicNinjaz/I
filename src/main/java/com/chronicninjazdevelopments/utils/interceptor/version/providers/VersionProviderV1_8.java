package com.chronicninjazdevelopments.utils.interceptor.version.providers;

import com.chronicninjazdevelopments.utils.PacketUtil;
import com.chronicninjazdevelopments.utils.ReflectionUtils;
import com.chronicninjazdevelopments.utils.interceptor.PacketType;
import com.chronicninjazdevelopments.utils.interceptor.version.Version;
import com.chronicninjazdevelopments.utils.interceptor.version.VersionProvider;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Eisenberg on 5/24/2018
 */
public class VersionProviderV1_8 extends VersionProvider {
    public VersionProviderV1_8() {
        super(Version.V1_8);
    }


    @Override
    public PacketType getSignPacketType() {
        return PacketType.PacketPlayOutOpenSignEditor;
    }

    @Override
    public String[] getSignLines(Object[] lines) {
        try {
            for (Object line : lines) {
                System.out.println(line.getClass().getName());
            }
            Version version = getVersion();
            System.out.println(version.toString());
            List<String> lineStringList = new ArrayList<>();

            for (Object line : lines) {//{text='" + this.b + '\'
                String lineString = line.toString().split("\\{text='")[1].split("',")[0].trim();
                System.out.println(lineString);
                lineStringList.add(lineString);
            }
            return lineStringList.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new String[3];

    }

    @Override
    public Object setEditable(Sign sign, Player player) {
//        Location l = sign.getLocation();
//        TileEntitySign t = (TileEntitySign) ((CraftWorld) l.getWorld()).getTileEntityAt(l.getBlockX(), l.getBlockY(), l.getBlockZ());
//        t(((CraftPlayer) player).getHandle());
////        t.isEditable = true;
//        net.minecraft.server.v1_8_R3.BlockPosition blockPosition = new net.minecraft.server.v1_8_R3.BlockPosition(l.getX(), l.getY(), l.getZ());
//        PacketPlayOutOpenSignEditor packet = new PacketPlayOutOpenSignEditor(blockPosition);
//        return packet;

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
            Object connection = PacketUtil.getConnection(player);
            Object nmsPlayer = PacketUtil.getNMSPlayer(player);
            Object tileEntitySignObject = ReflectionUtils.getMethod(worldServer, "getTileEntity", blockPosition).invoke(worldServerObject, blockPositionObject);
            Class<?> tileEntitySign = tileEntitySignObject.getClass();
            Class<?> nmsPlayerClass = nmsPlayer.getClass();
//
//
//            Method method = ReflectionUtils.getMethod(tileEntitySign, "a", nmsPlayerClass);
//            method.setAccessible(true);
//            method.invoke(null, nmsPlayer);

            ReflectionUtils.setValue(tileEntitySignObject, tileEntitySign, true, "isEditable", true);
            ReflectionUtils.setValue(tileEntitySignObject, tileEntitySign, true, "h", nmsPlayer);


            // Attempt to make the TileEntity editable.

            // Packet data and sending.
            Object packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutOpenSignEditor").getConstructor(blockPosition).newInstance(blockPositionObject);
            return packet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
