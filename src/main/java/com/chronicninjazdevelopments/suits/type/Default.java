package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Color;

public class Default extends Suit {

    private Minion minion;

    public Default(Minion minion) {
        super(minion, SuitType.DEFUALT);
        this.minion = minion;
    }

    @Override
    public void activate() {
        setHealmet(new SkullFactory().setOwner(minion.getOwner().getName()).build());
        setChestplate(Color.fromBGR(0, 0, 0));
        setLeggings(Color.fromBGR(0, 0, 0));
        setBoots(Color.fromBGR(0, 0, 0));
    }
}