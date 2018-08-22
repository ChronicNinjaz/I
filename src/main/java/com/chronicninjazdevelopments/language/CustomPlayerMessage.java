package com.chronicninjazdevelopments.language;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum CustomPlayerMessage {
    LANGUAGE_SELECTED("newLanguageSelected"),
    NO_MINIONS("noMinions"),
    NO_PERMISSION("noPermission"),
    ADMIN_NO_MINIONS("adminNoMinions"),
    ADMIN_TARGET_NOT_ONLINE("adminTargetNotOnline"),
    ADMIN_TARGET_RESEACHED_BACKPACK_LIMIT("adminTargetReachedBackpackLimit"),
    ADMIN_COULDNT_FIND_MINION("adminCouldntFindMinion"),
    PLAYER_HEALED_MINION("playerHealedMinion"),
    PLAYER_MINION_AT_MAX_HEALTH("playerMinionIsMaxHealth"),
    PLAYER_FED_MINION("playerFedMinion"),
    PLAYER_MINION_AT_MAX_HUNGER("playerMinionIsAtMaxHunger"),
    PLAYER_RENAMED_MINION("playerRenamedMinion"),
    PLAYER_PICKED_UP_MINION("playerPickedUpMinion"),
    PLAYER_PLACED_MINION("playerPlacedMinion"),
    PLAYER_NOT_ENOUGH_FUNDS("playerNotEnoughFunds"),
    PLAYER_BOUGHT_ITEM("playerBoughtItem");

    private String identity;
    CustomPlayerMessage(String identity){
        this.identity = identity;
    }

    public void send(PlayerData playerData){
        playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("Languages." + playerData.getLanguageType() + ".messages." + this.identity)));
    }

    public void send(PlayerData playerData, Minion minion){
        playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("Languages." + playerData.getLanguageType() + ".messages." + this.identity)).replace("%name%", minion.getName()));
    }

    public void sendAdmin(PlayerData playerData, Player target){
        playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("Languages." + playerData.getLanguageType() + ".messages." + this.identity).replace("%target%", target.getName())));
    }

    public void sendAdmin(PlayerData playerData, String target){
        playerData.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Minions.getInstance().getConfiguration().getString("Languages." + playerData.getLanguageType() + ".messages." + this.identity).replace("%target%", target)));
    }
}
