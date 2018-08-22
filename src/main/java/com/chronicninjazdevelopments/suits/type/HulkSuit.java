package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Color;

public class HulkSuit extends Suit {

    public HulkSuit(Minion minion){
        super(minion, SuitType.HULK);
    }

    @Override
    public void activate() {
        Color color = Color.fromBGR(0, 153, 76);

        setHealmet(new SkullFactory().setOwner("Incredible_Hulk").build());
        setChestplate(color);
        setLeggings(color);
        setBoots(color);
    }
}
