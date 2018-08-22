package com.chronicninjazdevelopments.animations;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.types.Banker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BankerAnimation extends Animation {
    private Banker banker;

    public BankerAnimation(Banker banker){
        this.banker = banker;
    }

    @Override
    public void stop() {
        cancel();
    }

    @Override
    public void activate(long delay, long period) {
        this.runTaskTimer(Minions.getInstance(), delay, period);
    }

    @Override
    public void run() {
        if(!banker.isSpawned() || banker.getMinion() == null){
            cancel();
            return;
        }

        double total = 0;

        for(int x = banker.getLocation().getBlockX() - banker.getRadius(); x < banker.getLocation().getBlockX() + banker.getRadius(); x++){
            for(int y = banker.getLocation().getBlockY() - banker.getRadius(); y < banker.getLocation().getBlockY() + banker.getRadius(); y++){
                for(int z = banker.getLocation().getBlockZ() - banker.getRadius(); z < banker.getLocation().getBlockZ() + banker.getRadius(); z++) {
                    Location location = new Location(banker.getLocation().getWorld(), x, y, z);

                    if(location.getBlock().getType() == Material.CHEST){
                        Chest chest = (Chest) location.getBlock().getState();
                        Inventory inventory = chest.getInventory();
                        if(inventory != null){
                            for(ItemStack itemStack : inventory.getContents()){
                                if(itemStack == null)
                                    continue;

                                if(banker.getItems().containsKey(itemStack.getType())){

                                    double price = banker.getItems().get(itemStack.getType());

                                    if((banker.getBalance() + price) > banker.getMaxBalance())
                                        continue;

                                    banker.setBalance(banker.getBalance() + price);
                                    total += price;
                                    inventory.remove(itemStack);
                                    chest.update();
                                }
                            }
                        }
                    }
                }
            }
        }

        if(total > 0)
            banker.getOwner().sendMessage("[!] your sell minion just sold $" + total + " worth of items!");
    }
}
