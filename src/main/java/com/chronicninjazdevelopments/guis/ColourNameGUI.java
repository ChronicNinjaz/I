package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.regex.Pattern;

public class ColourNameGUI extends MenuFactory {

    public ColourNameGUI(Player player, Minion minion, int current){
        super(minion.getName(), 1);

        if(Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]").matcher(minion.getName()).replaceAll("").length() < current){
            player.closeInventory();
            return;
        }

        PlayerData data = Minions.getInstance().getMinionManager().getPlayerData(minion.getOwner());

        addItem(new MenuItem(0, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 1)
                .setDisplayName("&6Gold &7["  + (data.getUnlockedColours().contains(ChatColor.GOLD.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.GOLD.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&6").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(1, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 4)
                .setDisplayName("&6Yellow &7["  + (data.getUnlockedColours().contains(ChatColor.GOLD.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.YELLOW.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&e").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(2, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 3)
                .setDisplayName("&6Light Blue &7["  + (data.getUnlockedColours().contains(ChatColor.BLUE.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.BLUE.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&b").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(3, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 6)
                .setDisplayName("&6Red &7["  + (data.getUnlockedColours().contains(ChatColor.RED.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.RED.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&c").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(4, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 5)
                .setDisplayName("&6Green &7["  + (data.getUnlockedColours().contains(ChatColor.GREEN.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.GREEN.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&a").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(5, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 2)
                .setDisplayName("&6Purple &7["  + (data.getUnlockedColours().contains(ChatColor.LIGHT_PURPLE.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.LIGHT_PURPLE.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&5").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(6, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 11)
                .setDisplayName("&6Dark Blue &7["  + (data.getUnlockedColours().contains(ChatColor.DARK_BLUE.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.DARK_BLUE.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&1").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(7, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 15)
                .setDisplayName("&6Black &7["  + (data.getUnlockedColours().contains(ChatColor.BLACK.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.BLACK.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&0").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        addItem(new MenuItem(8, new ItemFactory(Material.STAINED_GLASS_PANE, 1, (byte) 7)
                .setDisplayName("&6Gray &7["  + (data.getUnlockedColours().contains(ChatColor.GRAY.name()) ? "&aUnlocked" : "&cLocked") + "&7]").build()){

            @Override
            public void click(Player player, ClickType clickType) {
                if(!data.getUnlockedColours().contains(ChatColor.GRAY.name())){
                    return;
                }

                minion.setName(new StringBuilder(minion.getName()).insert((2 * current) + current, "&7").toString());
                new ColourNameGUI(player, minion, current+1);
            }
        });

        openInventory(player);
    }
}
