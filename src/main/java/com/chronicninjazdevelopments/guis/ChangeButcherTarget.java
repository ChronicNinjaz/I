package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ChangeButcherTarget extends MenuFactory {

    public ChangeButcherTarget(PlayerData playerData, Butcher butcher){
        super(Minions.getInstance().getConfiguration().getString("settings.BUTCHER.targetInventory.invenName"), Minions.getInstance().getConfiguration().getInt("settings.BUTCHER.targetInventory.rows"));

        FileConfiguration config = Minions.getInstance().getConfiguration();
        for(String key : Minions.getInstance().getConfiguration().getConfigurationSection("settings.BUTCHER.targetInventory.contents").getKeys(false)){
            String path = "settings.BUTCHER.targetInventory.contents." + key + ".";
            int cost = config.getInt(path + "cost");
            String permission = config.getString(path + "permission");
            EntityType type = EntityType.valueOf(config.getString(path + "type"));
            int slot = config.getInt(path + "slot");
            String owner = config.getString(path + "skullItem");

            addItem(new MenuItem(slot, new SkullFactory().setOwner(owner).setLore(config.getStringList(path + "displayLore")).setDisplayName(config.getString(path + "displayName")).build()){
                @Override
                public void click(Player player, ClickType clickType) {
                    if(type == null){
                        return;
                    }

                    if(cost > 0){
                        if(Minions.getInstance().getEconomy().getBalance(player.getName()) < cost){
                            player.sendMessage("You do not have enough funds to do this.");
                            return;
                        }
                    }

                    if(!player.hasPermission(permission)){
                        CustomPlayerMessage.NO_PERMISSION.send(playerData);
                        return;
                    }

                    butcher.setTarget(type);
                    player.sendMessage("You have changed your minion target to " + type.name());
                    player.closeInventory();
                    return;
                }
            });
        }

        openInventory(playerData.getPlayer());
    }

}
