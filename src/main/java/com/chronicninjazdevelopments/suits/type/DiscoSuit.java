package com.chronicninjazdevelopments.suits.type;

import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.enums.SuitType;
import org.bukkit.Color;

import java.util.Random;

public class DiscoSuit extends Suit {

    public DiscoSuit(Minion minion) {
        super(minion, SuitType.DISCO);
    }

    @Override
    public void activate() {
        Color color = Color.fromBGR(20 + new Random().nextInt(225), 20 + new Random().nextInt(225), 20 + new Random().nextInt(225));

        setHealmet(color);
        setChestplate(color);
        setLeggings(color);
        setBoots(color);
    }
}
