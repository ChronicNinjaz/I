package com.chronicninjazdevelopments.animations;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Animation extends BukkitRunnable {

    protected long particleCooldown;

    public abstract void activate(long delay, long period);

    public abstract void stop();
}
