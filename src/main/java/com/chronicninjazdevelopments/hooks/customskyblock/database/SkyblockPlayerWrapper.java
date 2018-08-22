package com.chronicninjazdevelopments.hooks.customskyblock.database;


public class SkyblockPlayerWrapper {} /**extends com.cloudescape.database.wrappers.Wrapper  {

    public static final String COLLECTION_NAME = "skyblock_users";

    public SkyblockPlayerWrapper() {
    }

    public SkyblockPlayerContainer getPlayerByUUID(UUID uuid) {
        Document document = search("uniqueId", uuid);

        if (document == null) return null;

        return new SkyblockPlayerContainer(this, document);
    }
}
 */
