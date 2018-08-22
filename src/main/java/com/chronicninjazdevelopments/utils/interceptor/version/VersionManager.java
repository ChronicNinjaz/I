package com.chronicninjazdevelopments.utils.interceptor.version;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Created by Matthew Eisenberg on 5/24/2018 at 9:24 PM for the project Minions
 */
public class VersionManager implements Listener {
    private VersionProvider versionProvider;
    private Plugin plugin;

    public VersionManager(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    public VersionProvider getVersionProvider() {
        return versionProvider;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setupVersionProvider() throws VersionProviderSetupException {
        try {
            Version version = Version.getVersion();
            if (version == null) {
                return;
            }
            this.versionProvider = version.getVersionProviderClass().newInstance();
            this.plugin.getServer().getPluginManager().registerEvents(versionProvider, plugin);
            System.out.println("[VersionManager] Loaded version " + version.getVersionString());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new VersionProviderSetupException(e);
        }
    }
}
