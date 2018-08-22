package com.chronicninjazdevelopments.guis;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Banker;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.FakeSign;
import com.chronicninjazdevelopments.utils.gui.MenuFactory;
import com.chronicninjazdevelopments.utils.gui.MenuItem;
import com.chronicninjazdevelopments.utils.itemstack.ItemFactory;
import com.chronicninjazdevelopments.utils.itemstack.SkullFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.regex.Pattern;

public class InterfaceGUI extends MenuFactory {

    public enum Action {
        HEAL, FEED, PLACE_HOLDER, TURN_NORTH, TURN_SOUTH, TURN_EAST, TURN_WEST, PICK_UP, RENAME, CHANGE_SUIT, COLOUR_NAME, LINK_CHEST, UPGRADE_BUTCHER, WITHDRAW_BUTCHER_EXP, CHANGE_TARGET, WITHDRAW_BANKER_BALANCE;
    }

    public InterfaceGUI(Minion minion, Player player) {
        super(Minions.getInstance().getConfiguration().getString("Interface." + minion.getMinionType().name().toUpperCase() + ".inventoryName").replace("%player%", minion.getOwner().getName()).replace("%minion_type%", minion.getMinionType().name()), Minions.getInstance().getConfiguration().getInt("Interface." + minion.getMinionType().name().toUpperCase() + ".inventoryRows"));

        FileConfiguration config = Minions.getInstance().getConfiguration();
        PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(player);

        for (String key : Minions.getInstance().getConfiguration().getConfigurationSection("Interface." + minion.getMinionType().name().toUpperCase() + ".contents").getKeys(false)) {
            String path = "Interface." + minion.getMinionType().name().toUpperCase() + ".contents." + key + ".";

            addItem(new MenuItem(config.getInt(path + "slot"), config.isSet(path + "skullItem") ?
                    new SkullFactory().setOwner(config.getString(path + "skullItem"))
                            .setDisplayName(config.getString(path + "displayName"), minion)
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                            .setLore(config.getStringList(path + "displayLore"), minion).build()
                    :
                    new ItemFactory(Material.getMaterial(config.getString(path + "displayItem")), 1, (byte) config.getInt(path + "data"))
                            .setDisplayName(config.getString(path + "displayName"), minion)
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                            .setLore(config.getStringList(path + "displayLore"), minion)
                            .build()
            ) {
                @Override
                public void click(Player player, ClickType clickType) {
                    Action action = Action.valueOf(config.getString(path + "action"));
                    int cost = config.getInt(path + "cost");

                    switch (action) {
                        case HEAL:
                            if (minion.getHealth() >= minion.getMaxHealth()) {
                                CustomPlayerMessage.PLAYER_MINION_AT_MAX_HEALTH.send(playerData);
                                return;
                            }

                            if (clickType.isShiftClick() && clickType.isRightClick()) {
                                Minions.getInstance().getEconomy().withdrawPlayer(player.getName(), cost * 10);
                                minion.heal(10);
                                CustomPlayerMessage.PLAYER_HEALED_MINION.send(playerData);
                                player.closeInventory();
                                return;
                            }

                            if (Minions.getInstance().getEconomy().getBalance(player.getName()) < cost)
                                return;

                            Minions.getInstance().getEconomy().withdrawPlayer(player.getName(), cost);
                            minion.heal(1);
                            CustomPlayerMessage.PLAYER_HEALED_MINION.send(playerData);
                            player.closeInventory();
                            return;
                        case FEED:
                            if (minion.getHunger() >= minion.getMaxHunger()) {
                                CustomPlayerMessage.PLAYER_MINION_AT_MAX_HUNGER.send(playerData);
                                return;
                            }

                            if (clickType.isShiftClick() && clickType.isRightClick()) {
                                Minions.getInstance().getEconomy().withdrawPlayer(player.getName(), cost * 10);
                                minion.feed(10);
                                CustomPlayerMessage.PLAYER_FED_MINION.send(playerData);
                                player.closeInventory();
                                return;
                            }

                            if (Minions.getInstance().getEconomy().getBalance(player.getName()) < cost)
                                return;

                            Minions.getInstance().getEconomy().withdrawPlayer(player.getName(), cost);
                            minion.feed(1);
                            CustomPlayerMessage.PLAYER_FED_MINION.send(playerData);
                            player.closeInventory();
                            return;
                        case RENAME:
                            FakeSign fakeSign = new FakeSign(player, new String[]{"Enter a new name", "for your minion", "----------------"});
                            fakeSign.openGui(lines -> {

                                String name = lines.get(0);
                                minion.setName(name);
                                CustomPlayerMessage.PLAYER_RENAMED_MINION.send(playerData, minion);
                                player.closeInventory();
                            });
                            return;
                        case PICK_UP:
                            if (!minion.isSpawned())
                                return;
                            minion.setSpawned(false);
                            minion.getMinion().remove();
                            Minions.getInstance().getMinionManager().saveMinion(minion);
                            CustomPlayerMessage.PLAYER_PICKED_UP_MINION.send(playerData);
                            player.closeInventory();
                            return;
                        case CHANGE_SUIT:
                            new ChangeSuitGUI(playerData, minion);
                            return;
                        case COLOUR_NAME:
                            minion.setName(Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]").matcher(minion.getName()).replaceAll(""));
                            new ColourNameGUI(player, minion, 0);
                            return;
                        case WITHDRAW_BANKER_BALANCE:
                            if(minion.getMinionType() != MinionType.BANKER)
                                return;

                            player.closeInventory();

                            Banker banker = (Banker) minion;
                            if(banker.getBalance() < 1)
                            {
                                player.sendMessage("[!] The banker has no funds to withdraw!");
                                return;
                            }

                            Minions.getInstance().getEconomy().depositPlayer(player.getName(), banker.getBalance());
                            player.sendMessage("[!] You have withdrawn $" + banker.getBalance() + " from the banker!");
                            banker.setBalance(0);
                            return;
                        case LINK_CHEST:
                            if (minion.getMinionType() != MinionType.MINER)
                                return;

                            player.closeInventory();

                            if (Minions.getInstance().getMinionManager().getLinkingChest().containsKey(player))
                                return;

                            Minions.getInstance().getMinionManager().getLinkingChest().put(player, minion.getId());
                            player.sendMessage("[Minions] you have 30 seconds to click the chest you'd like to link to this minion!");

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (Minions.getInstance().getMinionManager().getLinkingChest().containsKey(player)) {
                                        Minions.getInstance().getMinionManager().getLinkingChest().remove(player);
                                        player.sendMessage("[!] You took too long to link the minion, please try again!");
                                        return;
                                    }
                                }
                            }.runTaskLater(Minions.getInstance(), 20 * 30L);
                            break;
                        case UPGRADE_BUTCHER:
                            if (minion.getMinionType() != MinionType.BUTCHER)
                                return;
                            Butcher butcher = (Butcher) minion;
                            if (butcher.getLevel() >= 5)
                                return;
                            int costt = butcher.butcherLevels[butcher.getLevel() + 1];
                            if (Minions.getInstance().getEconomy().getBalance(minion.getOwner().getName()) < costt)
                                return;
                            Minions.getInstance().getEconomy().withdrawPlayer(minion.getOwner().getName(), costt);
                            butcher.upgrade();
                            butcher.getOwner().sendMessage("You have upgraded your butcher minion!");
                            player.closeInventory();
                            return;
                        case CHANGE_TARGET:
                            if(minion.getMinionType() != MinionType.BUTCHER)
                                return;
                            new ChangeButcherTarget(playerData, (Butcher) minion);
                            return;
                        case WITHDRAW_BUTCHER_EXP:
                            if (minion.getMinionType() != MinionType.BUTCHER)
                                return;
                            Butcher butcher1 = (Butcher) minion;
                            if(butcher1.getExp() < 1)
                                return;

                            butcher1.withdrawExp();
                            player.sendMessage("You have withdrawn the exp from the butcher!");
                            player.closeInventory();
                            return;
                        case TURN_EAST:
                            Location east = minion.getLocation().clone();
                            east.setYaw(270);
                            minion.setLocation(east);
                            minion.getMinion().teleport(east);
                            player.closeInventory();
                            return;
                        case TURN_WEST:
                            Location west = minion.getLocation().clone();
                            west.setYaw(90);
                            minion.setLocation(west);
                            minion.getMinion().teleport(west);
                            player.closeInventory();
                            return;
                        case TURN_NORTH:
                            Location north = minion.getLocation().clone();
                            north.setYaw(180);
                            minion.setLocation(north);
                            minion.getMinion().teleport(north);
                            player.closeInventory();
                            return;
                        case TURN_SOUTH:
                            Location south = minion.getLocation().clone();
                            south.setYaw(360);
                            minion.setLocation(south);
                            minion.getMinion().teleport(south);
                            player.closeInventory();
                            return;
                        case PLACE_HOLDER:
                            return;
                        default:
                            return;
                    }
                }
            });

            openInventory(playerData.getPlayer());
        }
    }

}
