package com.chronicninjazdevelopments.managers.commands;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class Command extends BukkitCommand {
    private String command;
    private boolean consoleUsage;

    public Command(String command, boolean consoleUsage){
        super(command);

        this.command        = command;
        this.consoleUsage   = consoleUsage;

        registerCommand();
    }

    public abstract void execute(CommandSender sender, String[] args);

    public void registerCommand(){
        try {
            Field filed = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            filed.setAccessible(true);

            CommandMap commandMap = (CommandMap) filed.get(Bukkit.getServer());

            if(this.getClass().isAnnotationPresent(CommandInformation.class)){
                CommandInformation info = this.getClass().getAnnotation(CommandInformation.class);

                setPermission(info.permission());
                setDescription(info.description());
                setAliases(Arrays.asList(info.aliases()));
                setName(this.command);
                setUsage(info.usage());
                setPermissionMessage(info.permissionMessage());
            }

            commandMap.register(command, this);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&l[Minion Commands] &6&lRegistered new command &e&l/" + this.command));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            PlayerData playerData = Minions.getInstance().getMinionManager().getPlayerData(player);

            if(this.getClass().isAnnotationPresent(CommandInformation.class)){
                CommandInformation info = this.getClass().getAnnotation(CommandInformation.class);

                if(!player.hasPermission(info.permission())){
                    CustomPlayerMessage.NO_PERMISSION.send(playerData);
                    return false;
                }

            }
        }

        execute(commandSender, args);
        return true;
    }
}
