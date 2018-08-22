package com.chronicninjazdevelopments.utils;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.utils.interceptor.PacketInterceptorDuplex;
import com.chronicninjazdevelopments.utils.interceptor.PacketListener;
import com.chronicninjazdevelopments.utils.interceptor.PacketType;
import com.chronicninjazdevelopments.utils.interceptor.version.Version;
import com.chronicninjazdevelopments.utils.interceptor.version.VersionProvider;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FakeSign {
    private Player player;
    private String[] defaultLines;
    private List<String> signLines;

    private Material originalBlockTypeUnderSign;
    private Material originalBlockTypeSign;

    public FakeSign(Player player, String[] defaultLines) {

        this.player = player;
        this.defaultLines = defaultLines;
        this.signLines = new ArrayList<>();
        final VersionProvider versionProvider = Minions.getInstance().getVersionManager().getVersionProvider();
        PacketInterceptorDuplex.addListener(new PacketListener(versionProvider.getSignPacketType()) {

            @Override
            public void handlePacketIncoming(Player player, PacketType type, Object packet) {
                Class<?> packetClazz = packet.getClass();

                if (versionProvider.getVersion() != Version.V1_8) {

                    try {

                        Field field = packetClazz.getDeclaredField("b");
                        field.setAccessible(true);

                        String[] lines = (String[]) field.get(packet);
                        signLines.addAll(Arrays.asList(lines));

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                Block block = player.getWorld().getBlockAt(player.getLocation().add(0, 85, 0));
                                block.getRelative(BlockFace.UP).setType(originalBlockTypeSign);
                                block.setType(originalBlockTypeUnderSign);

                                Location location = block.getLocation();
                                int radius = 10;

                                for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                                    for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                                        for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                                            Block foundBlock = location.getWorld().getBlockAt(x, y, z);

                                            if (foundBlock.getType() == Material.SIGN_POST || foundBlock.getType() == Material.SIGN) {
                                                foundBlock.setType(Material.AIR);
                                            }

                                            if (foundBlock.getType() == Material.SPONGE) {
                                                foundBlock.setType(Material.AIR);
                                            }
                                        }
                                    }
                                }

                            }
                        }.runTaskLater(Minions.getInstance(), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //1.8
                    try {

                        Field field = packetClazz.getDeclaredField("b");
                        field.setAccessible(true);

                        Object[] lines = (Object[]) field.get(packet);
                        String[] strings = versionProvider.getSignLines(lines);
                        System.out.println(strings.toString()
                        );
                        signLines.addAll(Arrays.asList(strings));

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                Block block = player.getWorld().getBlockAt(player.getLocation().add(0, 85, 0));
                                block.getRelative(BlockFace.UP).setType(originalBlockTypeSign);
                                block.setType(originalBlockTypeUnderSign);

                                Location location = block.getLocation();
                                int radius = 10;

                                for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
                                    for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                                        for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                                            Block foundBlock = location.getWorld().getBlockAt(x, y, z);

                                            if (foundBlock.getType() == Material.SIGN_POST || foundBlock.getType() == Material.SIGN) {
                                                foundBlock.setType(Material.AIR);
                                            }

                                            if (foundBlock.getType() == Material.SPONGE) {
                                                foundBlock.setType(Material.AIR);
                                            }
                                        }
                                    }
                                }

                            }
                        }.runTaskLater(Minions.getInstance(), 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void handlePacketOutgoing(Player player, PacketType type, Object packet) {
            }
//        PacketInterceptorDuplex.addListener(new PacketListener(PacketType.PLAY_OUT_SIGN_EDITOR) {
//
//            @Override
//            public void handlePacketIncoming(Player player, PacketType type, Object packet) {
//
//                Class<?> packetClazz = packet.getClass();
//
//                try {
//
//                    Field field = packetClazz.getDeclaredField("b");
//                    field.setAccessible(true);
//
//                    String[] lines = (String[]) field.get(packet);
//                    signLines.addAll(Arrays.asList(lines));
//
//                    new BukkitRunnable() {
//
//                        @Override
//                        public void run() {
//                            Block block = player.getWorld().getBlockAt(player.getLocation().add(0, 85, 0));
//                            block.getRelative(BlockFace.UP).setType(originalBlockTypeSign);
//                            block.setType(originalBlockTypeUnderSign);
//
//                            Location location = block.getLocation();
//                            int radius = 10;
//
//                            for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
//                                for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
//                                    for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
//                                        Block foundBlock = location.getWorld().getBlockAt(x, y, z);
//
//                                        if (foundBlock.getType() == Material.SIGN_POST || foundBlock.getType() == Material.SIGN) {
//                                            foundBlock.setType(Material.AIR);
//                                        }
//
//                                        if (foundBlock.getType() == Material.SPONGE) {
//                                            foundBlock.setType(Material.AIR);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }.runTaskLater(Minions.getInstance(), 1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void handlePacketOutgoing(Player player, PacketType type, Object packet) {
//                Class<?> packetClazz = packet.getClass();
//            }
        });
    }

    public FakeSign openGui(Callback<List<String>> callback) {

        try {

            Location location = player.getLocation();

            Block underBlock = player.getWorld().getBlockAt(location.add(0, 5, 0));
            Block signBlock;

            originalBlockTypeUnderSign = underBlock.getType();
            originalBlockTypeSign = underBlock.getRelative(BlockFace.UP).getType();

            underBlock.setType(Material.SPONGE);
            underBlock.getRelative(BlockFace.UP).setType(Material.SIGN_POST);
            signBlock = underBlock.getRelative(BlockFace.UP);

            if (signBlock.getType() != Material.SIGN_POST) {
                return this;
            }

            Sign sign = (Sign) signBlock.getState();

            int line = 1;

            for (String str : defaultLines) {
                sign.setLine(line, ChatColor.translateAlternateColorCodes('&', str));
                line += 1;
            }

            sign.update(true, false);


            // Craft world objects
            Object playerConnection = PacketUtil.getConnection(player);
            Object packet = Minions.getInstance().getVersionManager().getVersionProvider().setEditable(sign, player);
//            Object packet = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(Minions.getInstance().getVersionManager().getVersionProvider().getSignPacketType().getPacketName()).getConstructor(blockPosition).newInstance(blockPositionObject);
            sign.update(true, false);
            new BukkitRunnable() {

                @Override
                public void run() {

                    try {
                        ReflectionUtils.invokeMethod(playerConnection, Objects.requireNonNull(playerConnection).getClass(), "sendPacket", packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if (signLines.size() > 0) {
                                callback.call(signLines);
                                this.cancel();
                            }
                        }
                    }.runTaskTimer(Minions.getInstance(), 0L, 1);
                }
            }.runTaskLater(Minions.getInstance(), 2L);


//            new BukkitRunnable() {
//
//                @Override
//                public void run() {
//                    if (signLines.size() > 0) {
//                        callback.call(signLines);
//                        this.cancel();
//                    }
//                }
//            }.runTaskTimer(Minions.getInstance(), 0L, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }
}