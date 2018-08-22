package com.chronicninjazdevelopments.utils.interceptor.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketWriteEvent extends Event implements Cancellable {

    private static HandlerList list = new HandlerList();

    private Player player;
    private Class<?> packet;

    private boolean cancelled;

    public PacketWriteEvent(Player player, Class<?> packet) {
        this.player = player;
        this.packet = packet;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Class<?> getPacket() {
        return packet;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}
