package com.chronicninjazdevelopments.utils.interceptor;

import com.chronicninjazdevelopments.utils.interceptor.events.PacketReadEvent;
import com.chronicninjazdevelopments.utils.interceptor.events.PacketWriteEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PacketInterceptorDuplex extends ChannelDuplexHandler {


    private static final HashMap<PacketType, List<PacketListener>> listeners;

    static {
        listeners = new HashMap<>();
    }

    private Player player;

    public PacketInterceptorDuplex(Player player) {
        this.player = player;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Class<?> clazz = msg.getClass();
        PacketType type = PacketType.getPacketByName(clazz.getSimpleName());

        if (listeners.containsKey(type)) {
            for (PacketListener listener : listeners.get(type)) {
                listener.handlePacketOutgoing(player, type, msg);
            }
        }

        PacketWriteEvent event = new PacketWriteEvent(player, clazz);
        if (event.isCancelled()) return;
        Bukkit.getPluginManager().callEvent(event);
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Class<?> clazz = msg.getClass();
        PacketType type = PacketType.getPacketByName(clazz.getSimpleName());

        if (listeners.containsKey(type)) {
            for (PacketListener listener : listeners.get(type)) {
                listener.handlePacketIncoming(player, type, msg);
            }
        }

        PacketReadEvent event = new PacketReadEvent(player, clazz);
        if (event.isCancelled()) return;
        Bukkit.getPluginManager().callEvent(event);
        super.channelRead(ctx, msg);
    }

    public static void addListener(PacketListener listener) {
        List<PacketListener> listeners = (PacketInterceptorDuplex.listeners.containsKey(listener.getPacketType()) ? PacketInterceptorDuplex.listeners.get(listener.getPacketType()) : new ArrayList<>());
        listeners.add(listener);
        PacketInterceptorDuplex.listeners.put(listener.getPacketType(), listeners);
    }
}
