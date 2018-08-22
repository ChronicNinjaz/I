package com.chronicninjazdevelopments.utils.interceptor;

import com.chronicninjazdevelopments.utils.PacketUtil;
import io.netty.channel.Channel;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PacketInterceptorManager {

    public static void hookUser(Player player) {

        try {

            Object networkManager = PacketUtil.getNetworkManager(player);
            Channel foundChannel = Channel.class.cast(Objects.requireNonNull(networkManager).getClass().getField("channel").get(networkManager));
            foundChannel.pipeline().addBefore("packet_handler", "fortnite", new PacketInterceptorDuplex(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unhookUser(Player player) {

        try {

            Object networkManager = PacketUtil.getNetworkManager(player);
            Channel foundChannel = Channel.class.cast(Objects.requireNonNull(networkManager).getClass().getField("channel").get(networkManager));

            foundChannel.eventLoop().submit(() -> {
                foundChannel.pipeline().remove("fortnite");
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
