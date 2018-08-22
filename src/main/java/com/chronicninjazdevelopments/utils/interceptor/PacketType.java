package com.chronicninjazdevelopments.utils.interceptor;

public enum PacketType {

//    PacketPlayOutOpenSignEditor("PacketPlayOutOpenSignEditor"),//1.8
    PacketPlayOutOpenSignEditor("PacketPlayInUpdateSign"),//1.8
    PacketPlayOutOpenSignEditor1("PacketPlayOutOpenSignEditor"),//1.8
    PACKET_PLAY_IN_UPDATE_SIGN("PacketPlayInUpdateSign"), //1.12
    ;
    private String packetName;

    PacketType(String packetName) {
        this.packetName = packetName;
    }

    public String getPacketName() {
        return packetName;
    }

    public static PacketType getPacketByName(String name) {
        for (PacketType value : values()) {
            if (value.getPacketName().equalsIgnoreCase(name)) {
                return value;
            }
        }

        return null;
    }
}
