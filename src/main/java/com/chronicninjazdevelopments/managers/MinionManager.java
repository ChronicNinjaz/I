package com.chronicninjazdevelopments.managers;

import com.chronicninjazdevelopments.Minions;
import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.guis.InterfaceGUI;
import com.chronicninjazdevelopments.hooks.customskyblock.CustomSkyBlock;
import com.chronicninjazdevelopments.hooks.worldguard.WorldGuardHook;
import com.chronicninjazdevelopments.language.CustomPlayerMessage;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Banker;
import com.chronicninjazdevelopments.minions.types.Butcher;
import com.chronicninjazdevelopments.minions.types.Miner;
import com.chronicninjazdevelopments.player.PlayerData;
import com.chronicninjazdevelopments.utils.LocationUtils;
import com.chronicninjazdevelopments.utils.interceptor.PacketInterceptorManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MinionManager implements Listener {
    private ArrayList<PlayerData> activePlayers;
    private HashMap<Player, Integer> placingMinion;
    private HashMap<Player, Integer> linkingChest;
    private CustomSkyBlock customSkyBlock;
    private WorldGuardHook worldGuardHook;
    private BukkitTask minionHealth;

    public MinionManager() {

        Bukkit.getPluginManager().registerEvents(this, Minions.getInstance());

        this.activePlayers = new ArrayList<>();
        this.placingMinion = new HashMap<>();
        this.linkingChest = new HashMap<>();
        if(Minions.getInstance().getConfiguration().getBoolean("useCustomSkyBlock")){
            new BukkitRunnable() {

                @Override
                public void run() {
                    customSkyBlock = new CustomSkyBlock();
                }
            }.runTaskLater(Minions.getInstance(), 200L);

        }else {
            customSkyBlock = null;

            File folder = new File(Minions.getInstance().getDataFolder() + File.separator + "players");
            if (!folder.exists())
                folder.mkdirs();
        }


        if (Minions.getInstance().getConfiguration().getBoolean("useWorldGuardHook")) {
            worldGuardHook = new WorldGuardHook();
        }

        minionHealth = new BukkitRunnable() {
            @Override
            public void run() {
                if (activePlayers.isEmpty())
                    return;

                ArrayList<Minion> kill = new ArrayList<>();
                activePlayers.stream().forEach(playerData -> {
                    if (!playerData.getBackpack().getMinions().isEmpty())
                        playerData.getBackpack().getMinions().stream().forEach(minion -> {
                            if (minion.isSpawned()) {
                                if (minion.getHunger() > 0) {
                                    minion.setHunger(minion.getHunger() - 1);
                                } else {
                                    if (minion.getHealth() > 0) {
                                        minion.setHealth(minion.getHealth() - 1);
                                    } else {
                                        kill.add(minion);
                                    }
                                }
                            }
                        });
                });

                kill.stream().forEach(minion -> {
                    minion.kill();
                });

            }
        }.runTaskTimer(Minions.getInstance(), 0L, 20 * 60);
    }

    public boolean isMinion(Entity entity) {
        if (!(entity instanceof ArmorStand))
            return false;

        if (activePlayers.isEmpty())
            return false;

        if (getMinion(entity) == null)
            return false;

        return true;
    }

    public Minion getMinion(Entity entity) {
        if (activePlayers.isEmpty())
            return null;

        if (!(entity instanceof ArmorStand))
            return null;

        PlayerData data = activePlayers.stream().filter(playerData -> playerData.getBackpack().getMinions()
                .stream()
                .filter(minion -> minion.isSpawned() && minion.getMinion().getUniqueId() == entity.getUniqueId())
                .findAny()
                .orElse(null)
                != null).findAny().orElse(null);

        if (data != null)
            return data.getBackpack().getMinions().stream().filter(minion -> minion.isSpawned() && minion.getMinion().getUniqueId().equals(entity.getUniqueId())).findAny().orElse(null);

        return null;
    }

    public PlayerData getPlayerData(Player player) {
        return activePlayers.stream().filter(playerData -> playerData.getPlayer().getUniqueId().equals(player.getUniqueId())).findAny().orElse(null);
    }

    public void saveMinion(Minion minion) {
        PlayerData playerData = getPlayerData(minion.getOwner());
        /**
         *         if (customSkyBlock != null && customSkyBlock.isEnabled()) {
         *             playerData.getMinionWrapper().getUser(playerData.getPlayer().getUniqueId()).saveMinion(minion);
         *             return;
         *         } else
         */
            FileConfiguration config = playerData.getConfig();

            String path = "Minions." + minion.getId() + ".";
            config.set(path + "name", minion.getName());
            config.set(path + "hunger", minion.getHunger());
            config.set(path + "health", minion.getHealth());
            config.set(path + "maxHealth", minion.getMaxHealth());
            config.set(path + "maxHunger", minion.getMaxHunger());
            config.set(path + "suit", minion.getSuit().getSuitType().name());
            config.set(path + "spawned", minion.isSpawned());
            config.set(path + "boosted", minion.isBoosted());
            config.set(path + "type", minion.getMinionType().name());

            config.set(path + "trustedMembers", minion.getTrustedMembers());
            config.set(path + "location", minion.getLocation() == null ? "null" : LocationUtils.convertFullLocation(minion.getLocation().clone().add(-0.5, 0, -0.5)));

            switch (minion.getMinionType()) {
                case MINER:
                    Miner miner = (Miner) minion;
                    config.set(path + "minedBlocks", miner.getBlocksMined());
                    config.set(path + "linkedChest", miner.getLinkedChest() == null ? "null" : LocationUtils.convertFullLocation(miner.getLinkedChest().getLocation()));
                    break;
                case BUTCHER:
                    Butcher butcher = (Butcher) minion;
                    config.set(path + "exp", butcher.getExp());
                    config.set(path + "level", butcher.getLevel());
                    config.set(path + "radius", butcher.getRadius());
                    config.set(path + "target", butcher.getTarget().name());
                    break;
                case BANKER:
                    Banker banker = (Banker) minion;
                    config.set(path + "level", banker.getLevel());
                    config.set(path + "balance", banker.getBalance());
                    break;
            }

            try {
                playerData.getConfig().save(playerData.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public int getLowestFreeSpot(Player player) {
        PlayerData data = getPlayerData(player);
        for (int i = 1; i < data.getBackpack().getMaximumAllowed(); i++) {
            final int a = i;
            if (!data.getBackpack().getMinions().stream().anyMatch(minion -> minion.getId() == a)) {
                return a;
            }
        }
        return 1;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        for(Entity entity : event.getChunk().getEntities()){
            if(isMinion(entity)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void linkChest(PlayerInteractEvent event) {
        if (linkingChest.isEmpty())
            return;

        if (!linkingChest.keySet().contains(event.getPlayer()))
            return;

        event.setCancelled(true);

        if (!(event.getClickedBlock().getState() instanceof Chest)) {
            event.getPlayer().sendMessage("[!] You can only link this minion to a chest!");
            return;
        }

        PlayerData playerData = getPlayerData(event.getPlayer());
        int id = linkingChest.get(playerData.getPlayer());
        Minion minion = playerData.getBackpack().getMinions().stream().filter(miner -> miner.getId() == id).findFirst().orElse(null);
        if (minion == null || minion.getMinionType() != MinionType.MINER)
            return;
        Miner miner = (Miner) minion;
        Chest chest = (Chest) event.getClickedBlock().getState();
        miner.setLinkedChest(chest);
        linkingChest.remove(event.getPlayer());
        playerData.getPlayer().sendMessage("[!] You have successfully linked the miner minion to this chest!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (placingMinion.isEmpty())
            return;

        if (!placingMinion.keySet().contains(event.getPlayer()))
            return;

        event.setCancelled(true);

        PlayerData playerData = getPlayerData(event.getPlayer());

        if(playerData == null)
            return;

        /*if(customSkyBlock != null && customSkyBlock.isEnabled()){
            if(!customSkyBlock.hasPermissionToBuild(event.getPlayer())){
                CustomPlayerMessage.NO_PERMISSION.send(getPlayerData(event.getPlayer()));
                return;
            }
        }
        */

        if(worldGuardHook != null && worldGuardHook.isEnabled()){
            if(!worldGuardHook.canBuild(event.getPlayer(), event.getClickedBlock().getLocation())){
                CustomPlayerMessage.NO_PERMISSION.send(getPlayerData(event.getPlayer()));
                return;
            }
        }

        int id = placingMinion.get(event.getPlayer());
        placingMinion.remove(event.getPlayer());
        Minion minion = playerData.getBackpack().getMinions().stream().filter(target -> target.getId() == id).findFirst().orElse(null);
        if (minion != null) {
            minion.loadMinion(event.getClickedBlock().getLocation().clone().add(0, 2, 0));
            minion.setSpawned(true);
            CustomPlayerMessage.PLAYER_PLACED_MINION.send(playerData);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        activePlayers.add(new PlayerData(event.getPlayer()));
        PacketInterceptorManager.hookUser(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        PlayerData playerData = getPlayerData(event.getPlayer());
        if(playerData != null) {
            if (!playerData.getBackpack().getMinions().isEmpty()) {
                playerData.getBackpack().getMinions().stream().forEach(
                        minion -> {
                            saveMinion(minion);
                            if(minion.isSpawned()) {
                                minion.getMinion().remove();
                                minion.getAnimation().stop();
                            }
                        });
            }
        }

        activePlayers.remove(playerData);
        PacketInterceptorManager.unhookUser(event.getPlayer());
    }

    @EventHandler
    public void onMinionInteract(PlayerArmorStandManipulateEvent event) {
        if (activePlayers.isEmpty())
            return;

        if (!isMinion(event.getRightClicked()))
            return;

        event.setCancelled(true);

        Minion minion = getMinion(event.getRightClicked());

        if (!(minion.getOwner().getUniqueId() == event.getPlayer().getUniqueId() || minion.getTrustedMembers().stream().anyMatch(uuid -> uuid == event.getPlayer().getUniqueId())))
            return;

        if (event.getPlayer().isSneaking()) {
            minion.getAnimation().stop();
            minion.setSpawned(false);
            minion.getMinion().remove();
            Minions.getInstance().getMinionManager().saveMinion(minion);
            return;
        }

        new InterfaceGUI(minion, event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (isMinion(event.getEntity()))
            event.setCancelled(true);
    }

    public CustomSkyBlock getCustomSkyBlock() {
        return customSkyBlock;
    }

    public ArrayList<PlayerData> getActivePlayers() {
        return activePlayers;
    }

    public HashMap<Player, Integer> getPlacingMinion() {
        return placingMinion;
    }

    public HashMap<Player, Integer> getLinkingChest() {
        return linkingChest;
    }

    public BukkitTask getMinionHealth() {
        return minionHealth;
    }
}
