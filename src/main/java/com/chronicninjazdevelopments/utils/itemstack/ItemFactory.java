package com.chronicninjazdevelopments.utils.itemstack;

import com.chronicninjazdevelopments.enums.MinionType;
import com.chronicninjazdevelopments.minions.Minion;
import com.chronicninjazdevelopments.minions.types.Butcher;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemFactory extends Factory<ItemStack> {
    private ItemMeta itemMeta;

    public ItemFactory(Material material) {
        this(material, 1, (byte) 0);
    }

    public ItemFactory(Material material, int amount) {
        this(material, amount, (byte) 0);
    }

    public ItemFactory(Material material, int amount, byte data) {
        object = new ItemStack(material, amount, data);
        itemMeta = object.getItemMeta();
    }

    public ItemFactory setDisplayName(String displayName) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemFactory setDisplayName(String displayName, Minion minion) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName.replace("%player%", minion.getOwner().getName())
                .replace("%hunger%", String.valueOf(minion.getHunger()))
                .replace("%health%", String.valueOf(minion.getHealth()))
                .replace("%minion_type%", String.valueOf(minion.getMinionType().name()))
                .replace("%max_hunger%", String.valueOf(minion.getMaxHunger()))
                .replace("%max_health%", String.valueOf(minion.getMaxHealth()))
                .replace("%suit%", minion.getSuit().getSuitType().name())
                .replace("%minion_name%", minion.getName())
                .replace("%spawned%", String.valueOf(minion.isSpawned()))
                .replace("%boosted%", String.valueOf(minion.isBoosted()))));
        return this;
    }

    public ItemFactory setLore(List<String> lore) {
        List<String> currentLore = new ArrayList<>();
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        itemMeta.setLore(currentLore);
        return this;
    }

    public ItemFactory setLore(List<String> lore, Minion minion) {
        List<String> currentLore = new ArrayList<>();
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart.replace("%player%", minion.getOwner().getName())
                    .replace("%hunger%", String.valueOf(minion.getHunger()))
                    .replace("%health%", String.valueOf(minion.getHealth()))
                    .replace("%minion_type%", String.valueOf(minion.getMinionType().name()))
                    .replace("%max_hunger%", String.valueOf(minion.getMaxHunger()))
            .replace("%max_health%", String.valueOf(minion.getMaxHealth()))
            .replace("%suit%", minion.getSuit().getSuitType().name())
            .replace("%minion_name%", minion.getName())
            .replace("%spawned%", String.valueOf(minion.isSpawned()))
            .replace("%boosted%", String.valueOf(minion.isBoosted()))
            .replace("%butcher_level%", minion.getMinionType() == MinionType.BUTCHER ? String.valueOf(((Butcher)minion).getLevel()) : "&4ERROR")
            .replace("%butcher_upgrade_cost%", minion.getMinionType() == MinionType.BUTCHER ? ((Butcher)minion).getLevel() < 5 ? String.valueOf(Butcher.butcherLevels[((Butcher)minion).getLevel()+1]) : "&6Maxed" : "&4ERROR")
            .replace("%butcher_exp_collected%", minion.getMinionType() == MinionType.BUTCHER ? String.valueOf(((Butcher)minion).getExp()) : "&4ERROR")
            .replace("%butcher_exp_limit%", minion.getMinionType() == MinionType.BUTCHER ? String.valueOf(((Butcher)minion).getLevel() * 50) : "&4ERROR")));
        }

        itemMeta.setLore(currentLore);
        return this;
    }

    public ItemFactory addItemFlag(ItemFlag... flag){
        itemMeta.addItemFlags(flag);
        return this;
    }

    public ItemFactory setLore(String... lore) {
        List<String> currentLore = new ArrayList<>();
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        itemMeta.setLore(currentLore);
        return this;
    }

    public ItemFactory appendLore(String... lore) {
        List<String> currentLore = (itemMeta.getLore() == null ? new ArrayList<>() : itemMeta.getLore());
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        itemMeta.setLore(currentLore);
        return this;
    }

    public ItemFactory enchant(Enchantment... enchantments) {
        if(enchantments == null){
            return this;
        }

        for (Enchantment enchantment : enchantments) {
            itemMeta.addEnchant(enchantment, 1, false);
        }
        return this;
    }

    @Override
    public ItemStack build() {
        object.setItemMeta(itemMeta);
        return object;
    }
}
