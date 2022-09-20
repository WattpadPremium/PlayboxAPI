package fr.dwightstudio.dsmapi;

import fr.dwightstudio.dsmapi.pages.Page;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

/**
 * A basic menu with multiple pages.
 */
public abstract class Menu {

    /**
     * @return the name of the menu
     */
    public abstract String getName();

    /**
     * Gets the page with a given index.
     *
     * @param index the index of the page
     * @return the corresponding page
     *
     * @throws IllegalArgumentException if index is not valid
     */
    public Page getPage(int index) {
        Validate.isTrue(index >= 0 && index < getPageCount(), "Index must be between 0 and " + getPageCount() + " excluded");
        return getPages()[index];
    }

    /**
     * Gets a array that contains all the pages. This is just a copy of the original array, all the changes would be discarded.
     *
     * @return an array that contains all the pages
     */
    public abstract Page[] getPages();

    /**
     * @return the number of pages
     */
    public abstract int getPageCount();

    /**
     * Open the menu to the player.
     *
     * @param player the player to whom the menu is open
     * @return a MenuView of the menu by the player
     * @see MenuView
     */
    public MenuView open(Player player, int page) {
        return new MenuView(this, player, page);
    }
}
