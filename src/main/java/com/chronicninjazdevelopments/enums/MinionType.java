package com.chronicninjazdevelopments.enums;

public enum MinionType {
    MINER, BANKER, BUTCHER, HEALER;

    public static MinionType getFromName(String name) {
        for (MinionType minionType : values()) {
            if (minionType.name().equalsIgnoreCase(name)) {
                return minionType;
            }
        }
        return null;
    }

}
