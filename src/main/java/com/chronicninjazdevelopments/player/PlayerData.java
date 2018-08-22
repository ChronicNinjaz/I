package com.chronicninjazdevelopments.player;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.backpack.Backpack;
import com.chronicninjazdevelopments.enums.SuitType;
import com.chronicninjazdevelopments.hooks.customskyblock.database.MinionWrapper;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerData {
    private Backpack backpack;
    private Player player;

    private File file;
    private FileConfiguration config;

    private MinionWrapper minionWrapper;

    private String languageType;
    private ArrayList<String> unlockedSuits;
    private ArrayList<String> unlockedColours;
    private ArrayList<String> unlockedMobs;

    public PlayerData(Player player){
        this.player     = player;

        /*f (Minions.getInstance().getMinionManager()!=null&&Minions.getInstance().getMinionManager().getCustomSkyBlock() != null && Minions.getInstance().getMinionManager().getCustomSkyBlock().isEnabled()) {
            MinionContainer container = Minions.getInstance().getMinionManager().getCustomSkyBlock().getMinionWrapper().getUser(player.getUniqueId());

            this.backpack = new Backpack(this, null);
            this.languageType = container.getLanguage();
            this.minionWrapper = Minions.getInstance().getMinionManager().getCustomSkyBlock().getMinionWrapper();
            container.loadMinions(this);
        } else {
        */
            this.file           = new File(Minions.getInstance().getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId() + ".yml");

            if (!this.file.exists()) {
                try {

                    this.file.createNewFile();
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);
                    config.set("backpackLimit", Minions.getInstance().getConfiguration().getInt("backpackLimit"));
                    config.set("languageType", Minions.getInstance().getConfiguration().getString("defaultLanguage"));
                    config.set("unlockedSuits", null);
                    config.set("unlockedColours", null);

                    config.save(this.file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            this.config         = YamlConfiguration.loadConfiguration(this.file);

            this.backpack       = new Backpack(this, this.config);
            this.languageType   = this.config.getString("languageType");

            this.unlockedSuits  = (ArrayList) this.config.getStringList("unlockedSuits");
            this.unlockedSuits.add(SuitType.IRONMAN.name());

            this.unlockedColours = (ArrayList) this.config.getStringList("unlockedColours");
            this.unlockedColours.add(ChatColor.GOLD.name());
            this.unlockedColours.add(ChatColor.YELLOW.name());

            this.unlockedMobs = new ArrayList<>();
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public Player getPlayer() {
        return player;
    }

    public Backpack getBackpack(){
        return backpack;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ArrayList<String> getUnlockedSuits() {
        return unlockedSuits;
    }

    public ArrayList<String> getUnlockedColours(){return unlockedColours;}

    public MinionWrapper getMinionWrapper() {
        return minionWrapper;
    }
}
