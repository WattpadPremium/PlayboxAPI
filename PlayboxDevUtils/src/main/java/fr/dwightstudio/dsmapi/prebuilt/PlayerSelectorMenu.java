package fr.dwightstudio.dsmapi.prebuilt;

import fr.dwightstudio.dsmapi.Menu;
import fr.dwightstudio.dsmapi.MenuView;
import fr.dwightstudio.dsmapi.pages.Page;
import fr.dwightstudio.dsmapi.pages.PageType;
import fr.dwightstudio.dsmapi.utils.ItemCreator;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PlayerSelectorMenu extends Menu {


    private final String name;
    private final UUID[] players;
    private final Consumer<Player> action;

    /**
     * Creates a player selector menu.
     * The condition is applied when the menu is created (the established player list won't update).
     *
     * @param name the name of the menu
     * @param condition the filter used to determine if a player should be included in the selection
     * @param action an action to perform on/with the player
     */
    public PlayerSelectorMenu(String name, Predicate<Player> condition, Consumer<Player> action) {
        this.name = name;
        players = Bukkit.getOnlinePlayers().stream().filter(condition).flatMap(player -> Stream.of(player.getUniqueId())).toArray(UUID[]::new);

        this.action = action;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Page[] getPages() {
        Page[] pages = new Page[getPageCount()];

        for (int i = 0; i < getPageCount(); i++) {

            int finalI = i;

            pages[i] = new Page() {
                @Override
                public String getName() {
                    return name + " (" + (finalI + 1) + "/" + getPageCount() +")";
                }

                @Override
                public ItemStack[] getContent() {
                    ItemStack[] content = getPageType().getBlankArray();

                    for (int e = 0; e < 45 && finalI * 45 + e < players.length; e++) {
                        try {
                            content[e] = new ItemStack(Material.SKULL);
                            SkullMeta meta = (SkullMeta) content[e].getItemMeta();
                            meta.setOwner(Bukkit.getOfflinePlayer(players[finalI * 45 + e]).getPlayer().getName());
                            meta.setDisplayName(Bukkit.getOfflinePlayer(players[finalI * 45 + e]).getName());
                            content[e].setItemMeta(meta);
                        } catch (NoSuchFieldError error) {
                            content[e] = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) 3);
                            SkullMeta meta = (SkullMeta) content[e].getItemMeta();
                            meta.setOwner(Bukkit.getOfflinePlayer(players[finalI * 45 + e]).getName());
                            meta.setDisplayName(Bukkit.getOfflinePlayer(players[finalI * 45 + e]).getName());
                            content[e].setItemMeta(meta);
                        }
                    }

                    if (finalI != 0) content[45] = new ItemCreator(Material.ARROW).setName("§l<--").getItem();
                    if (finalI + 1 < getPageCount()) content[53] = new ItemCreator(Material.ARROW).setName("§l-->").getItem();

                    return content;
                }

                @Override
                public PageType getPageType() {
                    return PageType.DOUBLE_CHEST;
                }

                @Override
                public void onClick(MenuView view, ClickType clickType, int slot, ItemStack itemStack) {
                    if (itemStack == null) return;

                    switch (itemStack.getType()) {
                        case ARROW:
                            if (slot == 45) view.previousPage();
                            if (slot == 53) view.nextPage();
                            break;
                        default:
                            action.accept(Bukkit.getPlayer(players[view.getCurrentPageIndex() * 45 + slot]));
                            break;
                    }
                }
            };
        }

        return pages;
    }

    @Override
    public int getPageCount() {
        return  (players.length / 45) + (players.length % 45 != 0 ? 1 : 0);
    }
}
