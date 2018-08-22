package com.chronicninjazdevelopments.commands;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.guis.BackpackGUI;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.managers.commands.Command;
import com.chronicninjazdevelopments.managers.commands.CommandInformation;
import com.chronicninjazdevelopments.minions.types.Banker;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.minions.types.Miner;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

@CommandInformation(description = "Minion management commands", usage = "/admin", permission = "minion.admin", aliases = { "mm", "admin"})
public class AdminCommands extends Command {

    public AdminCommands(){
        super("minionManager", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            switch (args.length) {
                case 2:
                    if (args[0].equalsIgnoreCase("see")) {
                        Player target = Bukkit.getPlayer(args[1]);

                        if (target != null) {

                            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(target);

                            if (playerData.getBackpack().getMinions().isEmpty()) {
                                CustomPlayerMessage.ADMIN_NO_MINIONS.sendAdmin(Minions.getInstance().getMinionManager().getPlayerData((Player) sender), target);
                                return;
                            }

                            new BackpackGUI(playerData, (Player) sender);
                            return;
                        } else {
                            CustomPlayerMessage.ADMIN_TARGET_NOT_ONLINE.sendAdmin(Minions.getInstance().getMinionManager().getPlayerData((Player) sender), args[1]);
                            return;
                        }
                    }

                    break;

                case 3:

                    if (args[0].equalsIgnoreCase("give")) {

                        Player target = Bukkit.getPlayer(args[1]);

                        if (target != null) {
                            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(target);

                            if (playerData.getBackpack().getMinions().size() + 1 > playerData.getBackpack().getMaximumAllowed()) {
                                CustomPlayerMessage.ADMIN_TARGET_RESEACHED_BACKPACK_LIMIT.sendAdmin(Minions.getInstance().getMinionManager().getPlayerData((Player) sender), target);
                                return;
                            }

                            if (Arrays.stream(MinionType.values()).anyMatch(minionType -> minionType.toString().equalsIgnoreCase(args[2]))) {

                                MinionType minionType = MinionType.valueOf(args[2].toUpperCase());

                                switch (minionType) {
                                    case MINER:
                                        Miner miner = new Miner(Minions.getInstance().getMinionManager().getLowestFreeSpot(playerData.getPlayer()),"Miner", Minions.getInstance().getMinionManager().getPlayerData(target), false, new ArrayList<>(), 100, 100, 100, 100, null, null, false, 10, null);
                                        playerData.getBackpack().getMinions().add(miner);
                                        Minions.getInstance().getMinionManager().saveMinion(miner);
                                        break;
                                    case BUTCHER:
                                        Butcher butcher = new Butcher(Minions.getInstance().getMinionManager().getLowestFreeSpot(playerData.getPlayer()), "Butcher", Minions.getInstance().getMinionManager().getPlayerData(target), false, new ArrayList<>(), 100, 100, 100, 100, null, null, false, 0, 1, 15, EntityType.ZOMBIE);
                                        playerData.getBackpack().getMinions().add(butcher);
                                        Minions.getInstance().getMinionManager().saveMinion(butcher);
                                        break;
                                    case BANKER:
                                        Banker banker = new Banker(Minions.getInstance().getMinionManager().getLowestFreeSpot(playerData.getPlayer()), "Banker", Minions.getInstance().getMinionManager().getPlayerData(target), false, new ArrayList<>(), 100, 100, 100, 100, null, null, false, 1, 0);
                                        playerData.getBackpack().getMinions().add(banker);
                                        Minions.getInstance().getMinionManager().saveMinion(banker);
                                        break;
                                }

                                (sender).sendMessage("You have given " + target.getName() + " a " + minionType.toString() + " minion!");
                                return;

                            } else {
                                CustomPlayerMessage.ADMIN_COULDNT_FIND_MINION.sendAdmin(Minions.getInstance().getMinionManager().getPlayerData((Player) sender), args[2]);
                                return;
                            }
                        } else {
                            CustomPlayerMessage.ADMIN_TARGET_NOT_ONLINE.sendAdmin(Minions.getInstance().getMinionManager().getPlayerData((Player) sender), args[1]);
                            return;
                        }
                    }

                case 4:
                    if(args[0].equalsIgnoreCase("add") && args[1].equalsIgnoreCase("suit")){
                        Player target = Bukkit.getPlayer(args[2]);

                        if(target != null && target.isOnline()){
                            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(target);

                            if(playerData != null){

                                SuitType suitType = SuitType.valueOf(args[3]);

                                if(suitType != null){

                                }

                            }
                        }else {
                            CustomPlayerMessage.ADMIN_TARGET_NOT_ONLINE.send(Minions.getInstance().getMinionManager().getPlayerData((Player) sender));
                            return;
                        }
                    }

                default:
                    sendHelpPage(sender);
                    break;
            }
        }
        else {
            switch (args.length){
                case 3:
                    if (args[0].equalsIgnoreCase("give")) {

                        Player target = Bukkit.getPlayer(args[1]);

                        if (target != null) {
                            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(target);

                            if (playerData.getBackpack().getMinions().size() + 1 > playerData.getBackpack().getMaximumAllowed()) {
                                System.out.println(playerData.getPlayer().getName() + " has reached there backpack limit.");
                                return;
                            }

                            if (Arrays.stream(MinionType.values()).anyMatch(minionType -> minionType.toString().equalsIgnoreCase(args[2]))) {

                                MinionType minionType = MinionType.valueOf(args[2].toUpperCase());

                                switch (minionType) {
                                    case MINER:
                                        Miner miner = new Miner(Minions.getInstance().getMinionManager().getLowestFreeSpot(playerData.getPlayer()),"Miner", Minions.getInstance().getMinionManager().getPlayerData(target), false, new ArrayList<>(), 100, 100, 100, 100, null, null, false, 10, null);
                                        playerData.getBackpack().getMinions().add(miner);
                                        Minions.getInstance().getMinionManager().saveMinion(miner);
                                        break;
                                    case BUTCHER:
                                        Butcher butcher = new Butcher(Minions.getInstance().getMinionManager().getLowestFreeSpot(playerData.getPlayer()), "Butcher", Minions.getInstance().getMinionManager().getPlayerData(target), false, new ArrayList<>(), 100, 100, 100, 100, null, null, false, 0, 1, 15, EntityType.ZOMBIE);
                                        playerData.getBackpack().getMinions().add(butcher);
                                        Minions.getInstance().getMinionManager().saveMinion(butcher);
                                        break;
                                }

                                System.out.println("You have given " + target.getName() + " a " + minionType.toString() + " minion!");
                                return;

                            }
                        }
                    }
            }
        }
    }

    public void sendHelpPage(CommandSender sender){
        if(sender instanceof Player){
            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData((Player)sender);

            Minions.getInstance().getConfiguration().getStringList("Languages." + playerData.getLanguageType() + ".messages.minionManagementCommandHelpPage")
                    .forEach(key -> playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', key)));
        }
    }
}
