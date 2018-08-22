package com.chronicninjazdevelopments.animations;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.types.Miner;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.EulerAngle;

public class MineBlockAnimation extends Animation {
	private boolean broken;
	private boolean down;
	private double armPos;
	private Miner miner;

	public MineBlockAnimation(Miner miner){
		this.particleCooldown = 200;

		this.miner = miner;
		this.armPos = -0.1;
		this.down = true;
		this.broken = false;
	}

	@Override
	public void activate(long delay, long interval) {
		runTaskTimer(Minions.getInstance(), delay, interval);
	}

	@Override
	public void stop() {
		cancel();
	}

	@Override
	public void run() {
		if(!miner.isSpawned() || miner.getMinion() == null || miner == null){
			this.cancel();
			return;
		}

		miner.getMinion().setRightArmPose(new EulerAngle(-Math.abs(armPos), 0, 0));

		if(down){

			armPos-=0.2;

			if(armPos <= 0.6)
				down = false;

			if(!broken && armPos >= 1.2 && armPos <= 1.4){
				Block block = miner.getFacingBlock();

				if(block == null)
					return;

				if(block.getType() == Material.AIR)
					return;

				if(miner.getBlackList().contains(block.getType().name()))
					return;

				broken = true;
				if(miner.getLinkedChest() != null)
					miner.getLinkedChest().getInventory().addItem(miner.getOre(block.getType()));
				else
					block.getWorld().dropItem(block.getLocation().clone().add(0, 2, 0), miner.getOre(block.getType()));

				miner.setBlocksMined(miner.getBlocksMined()+1);

				if(broken)
					block.setType(Material.AIR);
			}

		}else{
			armPos+=0.2;

			if(armPos >= 2){
				down = true;
				broken = false;
			}
		}
	}
}
