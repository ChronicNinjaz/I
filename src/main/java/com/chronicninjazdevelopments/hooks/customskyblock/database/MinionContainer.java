package com.chronicninjazdevelopments.hooks.customskyblock.database;

public class MinionContainer {} /** extends com.cloudescape.database.container.Container {

    public MinionContainer(com.cloudescape.database.wrappers.Wrapper wrapper, Document document) {
        super(wrapper, document);
    }

    public UUID getUniqueID() {
        return (UUID) getValue("uniqueID", null);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUniqueID());
    }

    public int getBackpackLimit() {
        return (int) getValue("backpackLimit", 9);
    }

    public String getLanguage() {
        return (String) getValue("languageType", "English");
    }

    public List<String> getUnlockedSuits() {
        return (List<String>) getValue("unlockedSuits", new ArrayList<>());
    }

    public List<String> getUnlockedColours() {
        return (List<String>) getValue("unlockedColours", new ArrayList<>());
    }

    public void addUnlockedColour(String colour) {
        List<String> unlockedColours = getUnlockedColours();
        unlockedColours.add(colour);
        setValue("unlockedColours", unlockedColours);
        update();
    }

    public void addUnlockedSuit(SuitType type) {
        List<String> unlockedSuits = getUnlockedSuits();
        unlockedSuits.add(type.name());
        setValue("unlockedSuits", unlockedSuits);
        update();
    }

    /*
        String path = "Minions." + minion.getId() + ".";
        config.set(path + "hunger", minion.getHunger());
        config.set(path + "health", minion.getHealth());
        config.set(path + "maxHealth", minion.getMaxHealth());
        config.set(path + "maxHunger", minion.getMaxHunger());
        config.set(path + "suit", minion.getSuit().getSuitType().name());

        switch (minion.getMinionType()) {
            case MINER:
                Miner miner = (Miner) minion;
                config.set(path + "type", miner.getMinionType().name());
                config.set(path + "minedBlocks", miner.getBlocksMined());
                config.set(path + "linkedChest", miner.getLinkedChest() == null ? "null" : "world:x:y:Z");
                break;
        }
     */

/**
    public List<Minion> loadMinions(PlayerData data) {

        if (!(getWrapper() instanceof MinionWrapper)) return null;

        List<Minion> minions = new ArrayList<>();

        MinionWrapper wrapper = (MinionWrapper) getWrapper();
        Document document = getDocument();


        int id = 1;

        // Went past last one loaded.

        while (wrapper.getValue(document, "Minions." + id + ".type") != null) {
            Object value = wrapper.getValue(document, "Minions." + id + ".type");
            System.out.println(value.toString());
            int health = (int) wrapper.getValue(document, "Minions." + id + ".health");
            int hunger = (int) wrapper.getValue(document, "Minions." + id + ".hunger");
            int maxHealth = (int) wrapper.getValue(document, "Minions." + id + ".maxHealth");
            int maxHunger = (int) wrapper.getValue(document, "Minions." + id + ".maxHunger");
            MinionType minionType = MinionType.getFromName(value.toString());
            boolean isBoosted = (boolean) (wrapper.getValue(document, "Minions." + id + ".boosted") == null ? false : wrapper.getValue(document, "Minions." + id + ".boosted"));

            List<String> trustedMembers = (List<String>) wrapper.getValue(document, "Minions." + id + ".trustedMembers");
            ArrayList<UUID> finalTrustedMembers = new ArrayList<>();
            for (String member : trustedMembers)
                finalTrustedMembers.add(UUID.fromString(member));
            SuitType suitType;

            if (wrapper.getValue(document, "Minions." + id + ".suit") == null) {
                suitType = SuitType.DEFUALT;
            } else {
                suitType = SuitType.valueOf((String) wrapper.getValue(document, "Minions." + id + ".suit"));
            }

            String locationString = (String) wrapper.getValue(document, "Minions." + id + ".location");

            Location location = null;
            if (locationString != null && !locationString.equals("null")) {

                String[] locationPart = locationString.replaceAll(":", ",").split(",");
                location = new Location(Bukkit.getWorld(locationPart[0]), Double.parseDouble(locationPart[1]), Double.parseDouble(locationPart[2]), Double.parseDouble(locationPart[3]), Float.parseFloat(locationPart[4]), Float.parseFloat(locationPart[5]));
            }
            if (minionType == null) {
                minionType = MinionType.MINER;
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "NULL MINION TYPE");
            }
            Minion minion = new Minion(
                    id,
                    minionType.toString(),
                    data,
                    false,
                    finalTrustedMembers,
                    health,
                    hunger,
                    maxHealth,
                    maxHunger,
                    suitType,
                    location,
                    isBoosted,
                    minionType
            );

            switch (minionType) {
                case MINER:

                    Miner miner = new Miner(data,minion);

                    miner.setSuit(suitType.newSuit(minion));
                    miner.setBlocksMined((int) wrapper.getValue(document, "Minions." + id + ".minedBlocks"));
                    String chestLocation = (String) wrapper.getValue(document, "Minions." + id + ".chestLocation");
                    if (chestLocation!=null&&(!chestLocation.equals("null"))){

                        String[] chestLocationPart = chestLocation.split(";");
                        if (location != null) {
                            String[] locationPart = locationString.replaceAll(":", ",").split(",");
                            Location chestLocationPoint = new Location(Bukkit.getWorld(chestLocationPart[0]), Integer.parseInt(chestLocationPart[1]), Integer.parseInt(chestLocationPart[2]), Integer.parseInt(locationPart[3]), Float.parseFloat(chestLocationPart[4]), Float.parseFloat(chestLocationPart[5]));
                            if (chestLocationPoint != null && chestLocationPoint.getWorld() != null) {
                                if (chestLocationPoint.getBlock() instanceof Chest) {
                                    Chest chest = (Chest) chestLocationPoint.getBlock();
                                    miner.setLinkedChest(chest);
                                }
                            } else {
                                miner.setLinkedChest(null);
                            }
                        } else {
                            miner.setLinkedChest(null);
                        }
                    }

                    data.getBackpack().getMinions().add(miner);
                    break;
                case BUTCHER:

                    Butcher butcher = new Butcher(minion,data) ;

                    butcher.setSuit(suitType.newSuit(minion));
                    int level = (int) wrapper.getValue(document, "Minions." + id + ".level");
                    int exp = (int) wrapper.getValue(document, "Minions." + id + ".exp");
                    int radius = (int) wrapper.getValue(document, "Minions." + id + ".radius");
                    EntityType target = EntityType.valueOf((String) wrapper.getValue(document, "Minions." + id + ".target"));
                    butcher.setLevel(level);
                    butcher.setExp(exp);
                    butcher.setRadius(radius);
                    butcher.setTarget(target);

                    data.getBackpack().getMinions().add(butcher);
                    break;
            }

            id += 1;
        }

        return minions;
    }

    /*
      String path = "Minions." + key + ".";

                int id = Integer.parseInt(key);
                String name = config.getString(path + "name");
                int hunger = config.getInt(path + "hunger");
                int health = config.getInt(path + "health");
                int maxHealth = config.getInt(path + "maxHealth");
                int maxHunger = config.getInt(path + "maxHunger");
                SuitType suit = SuitType.valueOf(config.getString(path + "suit"));

                boolean spawned = config.getBoolean(path + "spawned");
                boolean boosted = config.getBoolean(path + "boosted");

                List<String> trustedMembers = config.getStringList(path + "trustedMembers");
                ArrayList<UUID> finalTrustedMembers = new ArrayList<>();
                for (String member : trustedMembers)
                    finalTrustedMembers.add(UUID.fromString(member));

                Location location = null;
                if(spawned)
                    location = LocationUtils.decodeFullLocation(config.getString(path + "location"));

                MinionType type = MinionType.valueOf(config.getString(path + "type"));

                switch (type){
                    case MINER:
                        int blocksMined = config.getInt(path + "blocksMined");

                        Location chestLocation = null;
                        if(!config.getString(path + "linkedChest").equalsIgnoreCase("null"))
                            chestLocation = LocationUtils.decodeFullLocation(config.getString(path + "linkedChest"));

                        Miner miner = new Miner(id, name, playerData, spawned, finalTrustedMembers, health, hunger, maxHealth, maxHunger, suit, location, boosted, blocksMined, chestLocation);
                        this.minions.add(miner);
                        break;

                    case BUTCHER:
                        int level = config.getInt(path + "level");
                        int exp = config.getInt(path + "exp");
                        int radius = config.getInt(path + "radius");
                        EntityType target = EntityType.valueOf(config.getString(path + "target"));

                        Butcher butcher = new Butcher(id, name, playerData, spawned, finalTrustedMembers, health, hunger, maxHealth, maxHunger, suit, location, boosted, exp, level, radius, target);
                        this.minions.add(butcher);
                        break;
                }
     */

/**
    public void saveMinions() {

        if (!(getWrapper() instanceof MinionWrapper)) return;

        PlayerData data = Minions.getInstance().getMinionManager().getPlayerData(getPlayer());

        int id = 1;
        for (Minion minion : data.getBackpack().getMinions()) {
            minion.setId(id);
            saveMinion(minion);
            id++;
        }
    }

    public void saveMinion(Minion minion) {
        if (!(getWrapper() instanceof MinionWrapper)) return;

        MinionWrapper wrapper = (MinionWrapper) getWrapper();
        Document document = getDocument();


        String path = "Minions." + minion.getId() + ".";
        wrapper.setValue(document, path + "name", minion.getName());
        wrapper.setValue(document, path + "hunger", minion.getHunger());
        wrapper.setValue(document, path + "health", minion.getHealth());
        wrapper.setValue(document, path + "maxHealth", minion.getMaxHealth());
        wrapper.setValue(document, path + "maxHunger", minion.getMaxHunger());
        wrapper.setValue(document, path + "suit", minion.getSuit().getSuitType().name());
        wrapper.setValue(document, path + "spawned", minion.isSpawned());
        wrapper.setValue(document, path + "boosted", minion.isBoosted());
        wrapper.setValue(document, path + "type", minion.getMinionType().toString());

        wrapper.setValue(document, path + "trustedMembers", minion.getTrustedMembers());
        wrapper.setValue(document, path + "location", minion.getLocation() == null ? "null" : LocationUtils.convertFullLocation(minion.getLocation().clone().add(-0.5, 0, -0.5)));

        switch (minion.getMinionType()) {
            case MINER:
                Miner miner = (Miner) minion;
                wrapper.setValue(document, path + "type", miner.getMinionType().name());
                wrapper.setValue(document, path + "minedBlocks", miner.getBlocksMined());
                wrapper.setValue(document, path + "linkedChest", miner.getLinkedChest() == null ? "null" : LocationUtils.convertFullLocation(miner.getLinkedChest().getLocation()));
                break;
            case BUTCHER:
                Butcher butcher = (Butcher) minion;
                wrapper.setValue(document, path + "type", butcher.getMinionType().name());
                wrapper.setValue(document, path + "exp", butcher.getExp());
                wrapper.setValue(document, path + "level", butcher.getLevel());
                wrapper.setValue(document, path + "radius", butcher.getRadius());
                wrapper.setValue(document, path + "target", butcher.getTarget().name());
                break;
        }


        update();
    }
}
*/