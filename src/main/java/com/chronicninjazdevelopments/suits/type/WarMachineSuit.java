package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Color;

/**
 * Created by Matthew E on 5/4/2018.
 */
public class WarMachineSuit extends Suit {
    public WarMachineSuit(Minion minion) {
        super(minion, SuitType.WAR_MACHINE);
    }

    @Override
    public void activate() {
        Color color = Color.GRAY;
        setHealmet(new SkullFactory().setOwner("soundank").build());
        setChestplate(color);
        setLeggings(color);
        setBoots(color);
    }
}
