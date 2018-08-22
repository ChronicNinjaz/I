package com.chronicninjazdevelopments.utils.itemstack;

import com.chronicninjazdevelopments.minions.Minion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SkullFactory extends Factory<ItemStack> {

    private SkullMeta skullMeta;

    /**
     * Construct a newdb skull.
     */
    public SkullFactory() {
        this.object = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        this.skullMeta = (SkullMeta) object.getItemMeta();
    }

    /**
     * Set the Display name of the {@link ItemStack}.
     * @param displayName - displayname.
     */
    public SkullFactory setDisplayName(String displayName) {
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public SkullFactory setDisplayName(String displayName, Minion minion) {
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName.replace("%player%", minion.getOwner().getName())
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

    public SkullFactory setLore(List<String> lore) {
        List<String> currentLore = new ArrayList<>();
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        skullMeta.setLore(currentLore);
        return this;
    }


    public SkullFactory setLore(List<String> lore, Minion minion) {
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
                    .replace("%boosted%", String.valueOf(minion.isBoosted()))));
        }
        skullMeta.setLore(currentLore);
        return this;
    }



    /**
     * Set the amount of the {@link ItemStack}
     * @param amount - amount
     */
    public SkullFactory setAmount(int amount) {
        object.setAmount(amount);
        return this;
    }

    /**
     * Set the owner of a skull.
     * @param owner - the owner
     */
    public final SkullFactory setOwner(String owner) {
        skullMeta.setOwner(owner);
        return this;
    }

    /**
     * Set the lore of an {@link ItemStack}.
     * @param lore - the lore.
     */
    public SkullFactory setLore(String... lore) {
        List<String> currentLore = new ArrayList<>();
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        skullMeta.setLore(currentLore);
        return this;
    }

    /**
     * Append the current lore of an {@link ItemStack}.
     * @param lore - the additions to the lore.
     */
    public SkullFactory appendLore(String... lore) {
        List<String> currentLore = (skullMeta.getLore() == null ? new ArrayList<>() : skullMeta.getLore());
        for (String lorePart : lore) {
            currentLore.add(ChatColor.translateAlternateColorCodes('&', lorePart));
        }
        skullMeta.setLore(currentLore);
        return this;
    }

    /**
     * Enchant the {@link ItemStack}
     * @param enchantments - the enchantments.
     */
    public SkullFactory enchant(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            skullMeta.addEnchant(enchantment, 1, false);
        }
        return this;
    }

    /**
     * Add itemflags to {@link ItemStack}
     * @param flags - flags
     */
    public SkullFactory addItemFlag(ItemFlag... flags) {
        skullMeta.addItemFlags(flags);
        return this;
    }

    /**
     * Construct the final {@link ItemStack}.
     * @return ItemStack
     */
    @Override
    public ItemStack build() {
        object.setItemMeta(skullMeta);
        return object;
    }
}
