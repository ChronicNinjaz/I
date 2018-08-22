package com.chronicninjazdevelopments;

import com.chronicninjazdevelopments.commands.AdminCommands;
import com.chronicninjazdevelopments.commands.MinionCommand;
import com.chronicninjazdevelopments.commands.TemporaryCmd;
import com.chronicninjazdevelopments.managers.MinionManager;
import com.chronicninjazdevelopments.managers.commands.CommandManager;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.gui.MenuManager;
import com.chronicninjazdevelopments.utils.interceptor.PacketInterceptorManager;
import com.chronicninjazdevelopments.utils.interceptor.version.VersionManager;
import com.chronicninjazdevelopments.utils.interceptor.version.VersionProviderSetupException;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class Minions extends JavaPlugin {

    private static Minions instance;
    private MinionManager minionManager;
    private File configurationFile;
    private FileConfiguration configuration;
    private CommandManager commandManager;
    private VersionManager versionManager;
    private Economy economy = null;

    public void onEnable() {
        instance = this;

        configurationFile = new File(getDataFolder(), "config.yml");
        configuration = YamlConfiguration.loadConfiguration(configurationFile);

        versionManager = new VersionManager(this);
        try {
            versionManager.setupVersionProvider();
        } catch (VersionProviderSetupException e) {
            e.printStackTrace();
            System.out.println("[VersionManager] Failed to setup NMS");
            return;
        }

        try {
            Reader defualtValues = new InputStreamReader(Minions.getInstance().getResource("config.yml"), "UTF8");
            configuration.setDefaults(YamlConfiguration.loadConfiguration(defualtValues));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!configurationFile.exists())
            saveResource("config.yml", false);


        minionManager = new MinionManager();
        commandManager = new CommandManager();

        commandManager.addCommand(new MinionCommand());
        commandManager.addCommand(new AdminCommands());
        commandManager.addCommand(new TemporaryCmd());

        Bukkit.getPluginManager().registerEvents(new MenuManager(), this);

        setupEconomy();

        Bukkit.getOnlinePlayers().stream().forEach(player -> {
            minionManager.getActivePlayers().add(new PlayerData(player));
            PacketInterceptorManager.hookUser(player);
        });
    }

    public void onDisable() {

        Bukkit.getOnlinePlayers().forEach(PacketInterceptorManager::unhookUser);

        minionManager.getActivePlayers().stream().forEach(playerData -> {
            /*if (minionManager.getCustomSkyBlock() != null && minionManager.getCustomSkyBlock().isEnabled()) {
                playerData.getMinionWrapper().getUser(playerData.getPlayer().getUniqueId()).saveMinions();
            } else {
            */
                playerData.getBackpack().getMinions().stream().forEach(
                        minion -> {
                            minionManager.saveMinion(minion);

                            if (minion.isSpawned()) {
                                minion.getMinion().remove();
                                minion.getAnimation().stop();
                            }
                        }
                );
        });

        minionManager.getActivePlayers().clear();
        minionManager.getMinionHealth().cancel();
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    public static Minions getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }

    public MinionManager getMinionManager() {
        return minionManager;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public VersionManager getVersionManager() {
        return versionManager;
    }
}
