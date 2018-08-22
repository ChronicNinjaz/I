package com.chronicninjazdevelopments.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class PageMenuFactory implements InventoryHolder {
    private final Inventory inventory;
    private final String title;
    private final int rows;
    private List<MenuItem> items;
    private MenuItem border;
    private final int size;
    private int perPage;

    /**
     * Construct the MenuManager.
     *
     * @param title   - the title.
     * @param rows    - the rows.
     * @param perPage
     */
    public PageMenuFactory(String title, int rows, int perPage) {
        this.title = title;
        this.rows = rows;

        this.size = (rows * 9);
        this.perPage = perPage;

        items = new ArrayList<>();
        inventory = Bukkit.createInventory(this, this.size, ChatColor.translateAlternateColorCodes('&', title));
    }

    /**
     * Add a newdb item to the inventory.
     *
     * @param item - the item.
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Open inventory.
     *
     * @param player - the player who you open it for.
     */
    public void openInventory(Player player, int page) {

        if (inventory == null) return;

        inventory.clear();

        List<Integer> takenSlots = new ArrayList<>();

        // List of items for the current page the user is on.
        List<MenuItem> items = getItems(page);

        for (MenuItem item : items) {
            inventory.setItem(item.getIndex(), item.getItemStack());
            takenSlots.add(item.getIndex());
        }
        if (border != null) {
            int i = getRows() * 9;
            for (int j = 0; j < i; j++) {
                if (!takenSlots.contains(j)) {
                    MenuItem menuItem = new MenuItem(j, border.getItemStack()) {
                        @Override
                        public void click(Player player, ClickType clickType) {

                        }
                    };
                    inventory.setItem(j, menuItem.getItemStack());
                }
            }
        }
        player.openInventory(inventory);
    }

    private int getPages() {

        int totalItems = items.size();
        int pages = (totalItems / perPage);

        if (pages < 1)
            pages = 1;

        return ((totalItems % perPage == 0 || totalItems <= perPage) ? pages : pages + 1);
    }

    private List<MenuItem> getItems(int page) {

        List<MenuItem> menuItems = new ArrayList<>();

        try {
            for (int i = ((page - 1) * perPage); i < (((page - 1) * perPage) + perPage); i++) {
                MenuItem item = items.get(i);
                menuItems.add(item);
            }
        } catch (IndexOutOfBoundsException e) { /* Ignored */ }

        return menuItems;
    }

    /**
     * Get the inventory instance.
     *
     * @return Inventory
     */
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the inventory title.
     *
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the number of rows.
     *
     * @return Number of rows (1 - 6).
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get items in the inventory.
     *
     * @return MenuItem - list
     */
    public List<MenuItem> getItems() {
        return items;
    }

    public MenuItem getBorder() {
        return border;
    }

    public void setBorder(MenuItem border) {
        this.border = border;
    }
}

