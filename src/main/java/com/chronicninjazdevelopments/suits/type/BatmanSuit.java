package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Color;

public class BatmanSuit extends Suit {

    public BatmanSuit(Minion minion){
        super(minion, SuitType.BATMAN);
    }

    @Override
    public void activate() {
        Color color = Color.fromBGR(0, 0, 0);
        setHealmet(new SkullFactory().setOwner("neillyken").build());
        setChestplate(color);
        setLeggings(color);
        setBoots(color);
    }
}
