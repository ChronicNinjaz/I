package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Color;

public class IronManSuit extends Suit {

    public IronManSuit(Minion minion){
        super(minion, SuitType.IRONMAN);
    }

    @Override
    public void activate() {
        setHealmet(new SkullFactory().setOwner("DavidPrime14").build());
        setChestplate(Color.fromBGR(0, 0, 193));
        setLeggings(Color.fromBGR(0, 0, 193));
        setBoots(Color.fromBGR(0, 0, 193));
    }
}
