package com.chronicninjazdevelopments.commands;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.guis.BackpackGUI;
import com.chronicninjazdevelopments.guis.LanguageSelector;
import com.chronicninjazdevelopments.guis.StoreGUI;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.managers.commands.Command;
import com.chronicninjazdevelopments.managers.commands.CommandInformation;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInformation(description = "Minion management commands", usage = "/minions", aliases = { "minions", "m", "minion", "robot", "robots" })
public class MinionCommand extends Command {

    public MinionCommand(){
        super("minions", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        switch (args.length){
            case 1:
                if(args[0].equalsIgnoreCase("backpack") || args[0].equalsIgnoreCase("bp")){
                    PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData((Player)sender);

                    if(playerData == null){
                        sender.sendMessage("There was en issue loading your data, please relog");
                        return;
                    }

                    if(playerData.getBackpack().getMinions().isEmpty()) {
                        CustomPlayerMessage.NO_MINIONS.send(playerData);
                        return;
                    }

                    new BackpackGUI(playerData);
                    return;
                }

                if(args[0].equalsIgnoreCase("store")){
                    new StoreGUI((Player)sender);
                    return;
                }
            case 2:
                if(args[0].equalsIgnoreCase("change")){
                    if(args[1].equalsIgnoreCase("language")){
                        new LanguageSelector(Minions.getInstance().getMinionManager().getPlayerData((Player)sender));
                        return;
                    }
                }
            default: sendHelpPage(sender); break;
        }
    }

    public void sendHelpPage(CommandSender sender){
        if(sender instanceof Player){
            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData((Player)sender);

            Minions.getInstance().getConfiguration().getStringList("Languages." + playerData.getLanguageType() + ".messages.minionCommandHelpPage")
                    .forEach(key -> playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', key)));
        }
    }
}
