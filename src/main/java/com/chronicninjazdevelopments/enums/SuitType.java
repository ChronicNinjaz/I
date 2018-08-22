package com.chronicninjazdevelopments.enums;

import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.suits.type.*;

public enum SuitType {

    DISCO(DiscoSuit.class),
    IRONMAN(IronManSuit.class),
    WAR_MACHINE(WarMachineSuit.class),
    HULK(HulkSuit.class),
    BATMAN(BatmanSuit.class),
    DEFUALT(Default.class);

    private Class<? extends Suit> suitClass;

    SuitType(Class<? extends Suit> suitClass) {
        this.suitClass = suitClass;
    }

    public Suit newSuit(Minion minion) {
        try {
            return this.suitClass.getConstructor(Minion.class).newInstance(minion);
        } catch (Exception e) {
            return null;
        }
    }

    public Class<? extends Suit> getSuitClass() {
        return suitClass;
    }
}
