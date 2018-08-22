package com.chronicninjazdevelopments.hooks.customskyblock.database;


import java.util.ArrayList;
import java.util.UUID;

public class MinionWrapper {} /** extends com.cloudescape.database.wrappers.Wrapper {

    public static final String COLLECTION_NAME = "miniondata";

    public Document createUser(UUID uuid) {

        if (search("uniqueID", uuid) == null) {
            Document document = createDocument("uniqueID", uuid);
            document = setValue(document, "backpackLimit", 9);
            document = setValue(document, "languageType", "English");
            document = setValue(document, "unlockedSuits", new ArrayList<String>());
            document = setValue(document, "unlockedColours", new ArrayList<String>());

            updateDocument(document);
            return document;
        }

        return null;
    }

    public MinionContainer getUser(UUID uuid) {

        Document document = search("uniqueID", uuid);

        if (document == null) {
            document = createUser(uuid);
        }

        return new MinionContainer(this, document);
    }
}
*/