package fr.dwightstudio.dsmapi.commands;

import fr.dwightstudio.dsmapi.Menu;
import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.pages.Page;
import fr.dwightstudio.dsmapi.pages.PageType;
import fr.dwightstudio.dsmapi.prebuilt.PlayerSelectorMenu;
import fr.dwightstudio.dsmapi.utils.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

// The class must extend Menu or SimpleMenu (if you just need a single page)
public class TestMenu extends Menu {

    // Give the name of the menu (can be used if a page hasn't name)
    @Override
    public String getName() {
        return ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "DSMAPI testing menu";
    }

    // Supply the pages to the menu. Not required for SimpleMenu as the object itself is a page.
    @Override
    public Page[] getPages() {
        Page[] pages = new Page[getPageCount()];

        // For each page of the menu, define its property
        pages[0] = new Page() {
            // Give the name displayed at the top of the inventory
            @Override
            public String getName() {
                return ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "DSMAPI testing menu page 1";
            }

            // Supply the content of the inventory
            @Override
            public ItemStack[] getContent() {

                // Methode to generate a 2D array of the shape of the inventory
                ItemStack[][] content = getPageType().getBlank2DArray();

                // Add the items
                content[1][4] = new ItemCreator(Material.APPLE).setName("Give Apple").getItem();
                content[2][8] = new ItemCreator(Material.ARROW).setName("Next Page").getItem();

                // Flatten the 2D Array (the methods return a 1D Array)
                return getPageType().flatten(content);
            }

            // Give the page type (see PageType https://github.com/Dwight-Studio/DSMAPI/blob/1.16/src/main/java/fr/dwightstudio/dsmapi/pages/PageType.java)
            @Override
            public PageType getPageType() {
                return PageType.CHEST;
            }

            // Event fired when the player click
            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack == null) return;

                switch (itemStack.getType()) {
                    case APPLE:
                        // Add a item to the player inventory
                        view.getPlayer().getInventory().addItem(new ItemStack(Material.APPLE));
                        break;
                    case ARROW:
                        // Display the next page
                        view.nextPage();
                        break;
                }
            }
        };

        // Second page
        pages[1] = new Page() {
            @Override
            public String getName() {
                return ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "DSMAPI testing menu page 2";
            }

            @Override
            public ItemStack[] getContent() {

                ItemStack[][] content = getPageType().getBlank2DArray();

                content[1][4] = new ItemCreator(Material.PAPER).setName("Apparently, it is working").getItem();
                content[3][0] = new ItemCreator(Material.ARROW).setName("Previous Page").getItem();
                content[3][8] = new ItemCreator(Material.ARROW).setName("Next Page").getItem();

                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.CHEST_PLUS;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack == null) return;

                switch (itemStack.getType()) {
                    case PAPER:
                        view.getPlayer().sendMessage("Yes, it is working well.");
                        break;
                    case ARROW:
                        if (itemStack.getItemMeta().getDisplayName().equals("Next Page")) {
                            view.nextPage();
                        } else {
                            view.previousPage();
                        }
                        break;
                }
            }
        };

        // Third Page
        pages[2] = new Page() {
            @Override
            public String getName() {
                return ChatColor.LIGHT_PURPLE + "" + ChatColor.ITALIC + "DSMAPI testing menu last page";
            }

            @Override
            public ItemStack[] getContent() {
                ItemStack[][] content = getPageType().getBlank2DArray();

                content[1][1] = new ItemCreator(Material.BEACON).setName("Run away!").getItem();
                content[2][0] = new ItemCreator(Material.ARROW).setName("Previous Page").getItem();

                return getPageType().flatten(content);
            }

            @Override
            public PageType getPageType() {
                return PageType.DISPENSER;
            }

            @Override
            public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                if (itemStack == null) return;

                switch (itemStack.getType()) {
                    case BEACON:
                        new PlayerSelectorMenu("Player selector test", player -> true, player -> player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, true))).open(view.getPlayer(), 0);
                        break;
                    case ARROW:
                        view.previousPage();
                        break;
                }
            }
        };

        // Return the array
        return pages;
    }

    // Give the number of pages
    @Override
    public int getPageCount() {
        return 3;
    }
}
