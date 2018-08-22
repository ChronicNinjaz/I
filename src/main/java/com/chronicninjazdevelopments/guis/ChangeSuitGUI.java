package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.suits.Suit;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ChangeSuitGUI extends MenuFactory {

    public ChangeSuitGUI(PlayerData playerData, Minion minion){
        super("Suit Menu", ((playerData.getUnlockedSuits().size() / 9) + 1));

        int slot = 0;
        for(SuitType suit : SuitType.values()){

            boolean unlocked = playerData.getUnlockedSuits().contains(suit.name());
            addItem(new MenuItem(slot, new ItemFactory(Material.STAINED_GLASS_PANE, 1, unlocked ? (byte) 5 : (byte) 14)
                    .setDisplayName("&3" + suit.name() + " " + (unlocked ? "&7[&aUnlocked&7]" : "&7[&cLocked&7]"))
                    .build()){
                @Override
                public void click(Player player, ClickType clickType) {

                    if(playerData.getUnlockedSuits().contains(suit.name())){
                        try {
                            Suit suit1 = suit.getSuitClass().getConstructor(Minion.class).newInstance(minion);
                            suit1.activate();
                            minion.setSuit(suit1);
                            player.closeInventory();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else {
                        return;
                    }
                }
            });

            slot++;
        }

        openInventory(playerData.getPlayer());

    }
}
