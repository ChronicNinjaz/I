package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Miner;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;

public class BackpackGUI extends MenuFactory {

    public BackpackGUI(PlayerData playerData){
        super(playerData.getPlayer().getName() + "'s Backpack", (playerData.getBackpack().getMaximumAllowed() / 9) + (playerData.getBackpack().getMaximumAllowed() % 9 > 0 ? 1 : 0));

        int slot = 0;
        for(Minion minion : playerData.getBackpack().getMinions()){
            addItem(new MenuItem(slot, new ItemFactory(Material.ARMOR_STAND)
            .setDisplayName("               &r" + minion.getName() + " &6(&3" + minion.getMinionType().name().toLowerCase() + "&6)")
            .setLore(getMinionDisplayLore(minion))
            .build()){

                @Override
                public void click(Player player, ClickType clickType) {
                    if(!minion.isSpawned()) {
                        Minions.getInstance().getMinionManager().getPlacingMinion().put(player, minion.getId());
                        player.closeInventory();
                    }
                    else{
                        minion.getAnimation().stop();
                        minion.setSpawned(false);
                        minion.getMinion().remove();
                        player.sendMessage("You have put " + minion.getName() + " back into the backpack!");
                        player.closeInventory();
                    }
                }

            });
            slot++;
        }
        openInventory(playerData.getPlayer());
    }

    public BackpackGUI(PlayerData playerData, Player admin){
        super(playerData.getPlayer().getName() + "'s Backpack", playerData.getBackpack().getMaximumAllowed() / 9);

        int slot = 0;
        for(Minion minion : playerData.getBackpack().getMinions()){
            addItem(new MenuItem(slot, new ItemFactory(Material.ARMOR_STAND)
                    .setDisplayName(minion.getName())
                    .setLore(getMinionDisplayLore(minion))
                    .build()));
        }
        openInventory(admin);
    }

    public ArrayList<String> getMinionDisplayLore(Minion minion){
        ArrayList<String> lore = new ArrayList<>();

        lore.add("&6Spawned &7[&e" + (minion.isSpawned() ? "&aYes" : "&cNo") + "&7] &6Boosted &7[&e" + (minion.isBoosted() ? "&aYes" : "&cNo") + "&7]");
        lore.add("&6Health &7[&e" + minion.getHealth() + "&7] &6Hunger &7[&e" + minion.getHunger() + "&7] &6Suit &7[&e" + minion.getSuit().getSuitType().name() + "&7]");
        lore.add("&6Trusted &7[&e" + (minion.getTrustedMembers().isEmpty() ? "None" : minion.getTrustedMembers().toString().replace("[", "").replace("]", ""))+ "&7]");

        switch (minion.getMinionType()){
            case MINER:
                Miner miner = (Miner) minion;
                lore.add("&6Mined &7[&e" + miner.getBlocksMined()+ "&7] &6Linked Chest &7[&e" + (miner.getLinkedChest() == null ? "&cNo" : "&aYes") + "&7]");
                break;
        }
        return lore;
    }

}
