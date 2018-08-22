package com.chronicninjazdevelopments.animations;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Healer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class HealerAnimation extends Animation {

    private Healer healer;
    public HealerAnimation(Healer healer){
        this.healer = healer;
    }

    @Override
    public void activate(long delay, long period) {
        runTaskTimer(Minions.getInstance(), delay, period);
    }

    @Override
    public void run() {
        if(!healer.isSpawned() || healer.getMinion() == null || healer == null){
            this.cancel();
            return;
        }

        for(Entity entity : healer.getMinion().getNearbyEntities(healer.getRadius(), healer.getRadius(), healer.getRadius())){
            if(entity instanceof ArmorStand){
                if(Minions.getInstance().getMinionManager().isMinion(entity)){
                    Minion minion = Minions.getInstance().getMinionManager().getMinion(entity);
                    if(minion.getHunger() < minion.getMaxHunger()){
                        minion.feed(1);
                    }else {
                        minion.heal(1);
                    }
                }
            }
        }
    }

    public void stop(){}
}
