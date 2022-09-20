package fr.dwightstudio.dsmapi;

import club.playbox.UtilsPlugin;
import fr.dwightstudio.dsmapi.pages.Page;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the view of a menu by a player. It is used to perform action and listen to events.
 */
public class MenuView implements Listener {

    private final Menu menu;

    private final Player player;
    private int currentPage;
    private Inventory inventory;
    private InventoryView view;

    /**
     * @param menu the menu to open
     * @param currentPage the index of the page to open
     * @throws IllegalArgumentException if one of the parameter is null, or the menu is invalid
     */
    public MenuView(Menu menu, Player player, int currentPage) {

        UtilsPlugin.getInstance().getServer().getPluginManager().registerEvents(this, UtilsPlugin.getInstance());

        Validate.notNull(menu, "Menu is null");
        Validate.notNull(player, "Player is null");

        this.menu = menu;
        this.player = player;

        Validate.notNull(menu.getName(), "Menu name is null");
        Validate.notNull(menu.getPages(), "Pages array is null");
        Validate.notEmpty(menu.getPages(), "Pages array is empty");
        Validate.noNullElements(menu.getPages(), "A page of the menu is null");

        this.setPage(currentPage);
    }

    /**
     * @return the player who is viewing the menu
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the current inventory view
     */
    public InventoryView getInventoryView() {
        return view;
    }

    /**
     * @return the menu of the MenuView
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * @return the page that the player is currently viewing
     */
    public Page getCurrentPage() {
        return this.menu.getPage(currentPage);
    }

    /**
     * @return the index of the current page
     */
    public int getCurrentPageIndex() {
        return currentPage;
    }

    /**
     * @param index the index of the page
     * @throws IllegalArgumentException if the page index is out of bounds or if the page is invalid
     */
    public void setPage(int index) {
        Validate.isTrue(index >= 0 && index < this.menu.getPageCount(), "Page index out of bounds");
        this.currentPage = index;

        Validate.notNull(this.menu.getPage(currentPage), "The page must not be null");
        Validate.notNull(this.menu.getPage(currentPage).getPageType(), "The page type must not be null");
        Validate.notNull(this.menu.getPage(currentPage).getContent(), "The content must not be null");

        ItemStack[] pageContent = this.menu.getPage(currentPage).getContent();

        if (this.menu.getPage(currentPage).getPageType().getInventoryType() == InventoryType.CHEST) {
            this.inventory = Bukkit.createInventory(null, this.menu.getPage(currentPage).getPageType().getSize(), this.getCurrentPage().getName() != null ? this.getCurrentPage().getName() : this.menu.getName());
        } else {
            this.inventory = Bukkit.createInventory(null, this.menu.getPage(currentPage).getPageType().getInventoryType(), this.getCurrentPage().getName() != null ? this.getCurrentPage().getName() : this.menu.getName());
        }

        Validate.isTrue(pageContent.length == this.inventory.getContents().length, "The length of the content does not correspond to the length declared in the page type");

        this.inventory.setContents(pageContent);

        this.view = this.player.openInventory(this.inventory);
    }

    /**
     * Reopens the current page.
     */
    public void reopen() {
        setPage(getCurrentPageIndex());
    }

    /**
     * Open the next page to the player.
     *
     * @return true if the page is effectively opened, otherwise false
     */
    public boolean nextPage() {
        if (this.currentPage + 1 < this.menu.getPageCount()) {
            this.setPage(this.currentPage + 1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Open the previous page to the player.
     *
     * @return true if the page is effectively opened, otherwise false
     */
    public boolean previousPage() {
        if (this.currentPage - 1 >= 0) {
            this.setPage(this.currentPage - 1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Close the menu.
     */
    public void close() {
        if (view != null) {
            this.view.close();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getView() == this.getInventoryView()) {

            if (event.getRawSlot() >= this.getCurrentPage().getContent().length || event.getRawSlot() < 0) return;

            UtilsPlugin.getInstance().getServer().getScheduler().runTask(UtilsPlugin.getInstance(), () -> MenuView.this.getCurrentPage().onClick(MenuView.this, event.getClick(), event.getRawSlot(), event.getCurrentItem()));

            event.setCancelled(true);
        }
    }
}
