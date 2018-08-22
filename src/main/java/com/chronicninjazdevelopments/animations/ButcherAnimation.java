package com.chronicninjazdevelopments.animations;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.types.Butcher;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ButcherAnimation extends Animation {
    private Butcher butcher;
    private boolean down;
    private double armPos;

    public ButcherAnimation(Butcher butcher){
        this.butcher = butcher;

        this.armPos = 1.6;
        this.down = true;
    }

    @Override
    public void stop() {
        cancel();
    }

    @Override
    public void run() {
        for(Entity entity : butcher.getMinion().getNearbyEntities(butcher.getRadius(), butcher.getRadius(), butcher.getRadius())){
            if(entity.getType() == butcher.getTarget()) {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        butcher.getMinion().setRightArmPose(new EulerAngle(-Math.abs(armPos), 0, 0));

                        if(down){
                            armPos-=0.1;

                            if(armPos <= -0.9) {
                                down = false;
                            }
                        }
                        else {
                            Location start = butcher.getLocation();
                            Location end = entity.getLocation().clone().add(0, 1, 0);
                            Vector dir = end.toVector().subtract(start.toVector());

                            for (double i = 1; i <= start.distance(end); i += 0.2) {
                                dir.multiply(i);
                                start.add(dir);
                                butcher.getMinion().getWorld().spigot().playEffect(start, Effect.COLOURED_DUST);
                                start.subtract(dir);
                                dir.normalize();
                            }

                            armPos = 1.6;
                            butcher.getMinion().setRightArmPose(new EulerAngle(-Math.abs(armPos), 0, 0));
                            entity.remove();
                            butcher.addExp(butcher.isBoosted() ? 2 : 1);

                            down = true;
                            cancel();
                        }
                    }
                }.runTaskTimer(Minions.getInstance(), 0L, 1L);
                return;
            }
        }
    }

    @Override
    public void activate(long delay, long period) {
        runTaskTimer(Minions.getInstance(), delay, period);
    }
}
