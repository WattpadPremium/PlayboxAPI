package fr.dwightstudio.dsmapi;

import fr.dwightstudio.dsmapi.pages.Page;
import org.bukkit.entity.Player;

/**
 * A simple menu with an single page.
 */
public abstract class SimpleMenu extends Menu implements Page {

    @Override
    public int getPageCount() {
        return 1;
    }

    @Override
    public Page getPage(int index) {
        return this;
    }

    public Page getPage() {
        return this;
    }

    @Override
    public Page[] getPages() {
        return new Page[] {this};
    }

    /**
     * Open the menu to the player.
     *
     * @param player the player to whom the menu is open
     * @return a MenuView of the menu by the player
     * @see MenuView
     */
    public MenuView open(Player player) {
        return super.open(player, 0);
    }
}
