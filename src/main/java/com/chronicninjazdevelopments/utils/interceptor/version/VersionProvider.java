package com.chronicninjazdevelopments.utils.interceptor.version;

import com.chronicninjazdevelopments.utils.interceptor.PacketType;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by Matthew Eisenberg on 5/24/2018 at 9:22 PM for the project Minions
 */
public abstract class VersionProvider implements Listener {
    private Version version;

    public VersionProvider(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    public abstract PacketType getSignPacketType();


    public abstract String[] getSignLines(Object[] lines);

    public abstract Object setEditable(Sign sign, Player player);
}
