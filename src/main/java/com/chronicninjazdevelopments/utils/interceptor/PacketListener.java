package com.chronicninjazdevelopments.utils.interceptor;

import org.bukkit.entity.Player;

public abstract class PacketListener {

    private final PacketType packetType;

    public PacketListener(PacketType packetType) {
        this.packetType = packetType;
    }

    public PacketType getPacketType() {
        return packetType;
    }

    public abstract void handlePacketIncoming(Player player, PacketType type, Object packet);

    public abstract void handlePacketOutgoing(Player player, PacketType type, Object packet);
}
