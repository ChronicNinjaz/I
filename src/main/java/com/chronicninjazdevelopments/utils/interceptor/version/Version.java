package com.chronicninjazdevelopments.utils.interceptor.version;

import com.chronicninjazdevelopments.utils.interceptor.version.providers.*;
import org.bukkit.Bukkit;

/**
 * By Matthew Eisenberg
 */
public enum Version {
    V1_8(VersionProviderV1_8.class),
    V1_9(VersionProviderV1_9.class),
    V1_10(VersionProviderV1_10.class),
    V1_11((VersionProviderV1_11.class)),
    V1_12(VersionProviderV1_12.class);

    private Class<? extends VersionProvider> versionProviderClass;

    Version() {

    }

    Version(Class<? extends VersionProvider> versionProviderClass) {
        this.versionProviderClass = versionProviderClass;
    }

    public static Version getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        for (Version minecraftVersion : Version.values()) {
            if (packageName.contains(minecraftVersion.toString().replaceAll("V", "").trim())) {
                return minecraftVersion;
            }
        }
        return null;
    }

    public Class<?> getNMSClass(String simpleName) {
        String className = "net.minecraft.server." + getVersionString() + "." + simpleName;
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVersionString() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public Class<? extends VersionProvider> getVersionProviderClass() {
        return versionProviderClass;
    }
}
