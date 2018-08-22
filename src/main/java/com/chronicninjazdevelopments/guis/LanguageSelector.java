package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;

public class LanguageSelector extends MenuFactory {

    public LanguageSelector(PlayerData playerData){
        super(Minions.getInstance().getConfiguration().getString("LanguageSelectorGUIName"), ((Minions.getInstance().getConfiguration().getConfigurationSection("Languages").getKeys(false).size() / 9) + 1));

        FileConfiguration config = Minions.getInstance().getConfiguration();

        int slot = 0;
        for(String key : config.getConfigurationSection("Languages").getKeys(false)){

            addItem(new MenuItem(slot, new ItemFactory(Material.getMaterial(config.getString("Languages." + key + ".displayItem")))
                    .setDisplayName(config.getString("Languages." + key + ".displayName"))
                    .setLore(config.getStringList("Languages." + key + ".displayLore"))
                    .enchant(playerData.getLanguageType().equalsIgnoreCase(key) ? Enchantment.DAMAGE_ALL : null)
                    .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS)
                    .build()){

                @Override
                public void click(Player player, ClickType clickType) {
                    playerData.setLanguageType(key);
                    CustomPlayerMessage.LANGUAGE_SELECTED.send(playerData);
                    playerData.getPlayer().closeInventory();
                }
            });

            slot++;
        }

        openInventory(playerData.getPlayer());
    }

}
