package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.hooks.customskyblock.database.SkyblockPlayerContainer;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;

public class StoreGUI extends MenuFactory {

    public StoreGUI(Player player){
        super(Minions.getInstance().getConfiguration().getString("StoreGUIName"), Minions.getInstance().getConfiguration().getInt("StoreGUIRows"));

        FileConfiguration config = Minions.getInstance().getConfiguration();
        for(String key : config.getConfigurationSection("Store").getKeys(false)){
            String path = "Store." + key + ".";

            addItem(new MenuItem(config.getInt(path + ".slot"), config.isSet(path + "skullItem") ?
                    new SkullFactory().setOwner(config.getString(path + "skullItem"))
                            .setDisplayName(config.getString(path + "displayName"))
                            .setLore(config.getStringList(path + "displayLore"))
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                            .build()
                    :
                    new ItemFactory(Material.getMaterial(config.getString(path + "displayItem")), 1, (byte) config.getInt(path + "data"))
                            .setDisplayName(config.getString(path + "displayName"))
                            .setLore(config.getStringList(path + "displayLore"))
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                            .build()){
                @Override
                public void click(Player player, ClickType clickType) {
                    /*if(cost > 0){
                        if(Minions.getInstance().getMinionManager().getCustomSkyBlock() != null && Minions.getInstance().getMinionManager().getCustomSkyBlock().isEnabled()){
                            SkyblockPlayerContainer skyBlockPlayer = Minions.getInstance().getMinionManager().getCustomSkyBlock().getSkyblockPlayerWrapper().getPlayerByUUID(player.getUniqueId());

                            if (skyBlockPlayer == null) {
                                player.sendMessage("There was an error processing this purchase!");
                                return;
                            }

                            double currentBalance = skyBlockPlayer.getBalance();

                            if (currentBalance - cost < 0) {
                                player.sendMessage("You do not have enough to purchase this minion! You require " + cost + " but you have " + currentBalance + ".");
                                return;
                            }

                            skyBlockPlayer.setBalance(currentBalance - cost);
                        }
                        else {
                            double currentBalance = Minions.getInstance().getEconomy().getBalance(player);

                            if (currentBalance - cost < 0) {
                                player.sendMessage("You do not have enough to purchase this minion! You require " + cost + " but you have " + currentBalance + ".");
                                return;
                            }

                            Minions.getInstance().getEconomy().withdrawPlayer(player, cost);
                        }
                    }*/

                    int cost = config.getInt(path + "cost");
                    double currentBalance = Minions.getInstance().getEconomy().getBalance(player);

                    if (currentBalance - cost < 0) {
                        CustomPlayerMessage.PLAYER_NOT_ENOUGH_FUNDS.send(Minions.getInstance().getMinionManager().getPlayerData(player));
                        return;
                    }

                    Minions.getInstance().getEconomy().withdrawPlayer(player, cost);

                    for(String command : config.getStringList(path + "commands")){
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                    }

                    CustomPlayerMessage.PLAYER_BOUGHT_ITEM.send(Minions.getInstance().getMinionManager().getPlayerData(player));
                }
            });
        }
        openInventory(player);
    }
}
