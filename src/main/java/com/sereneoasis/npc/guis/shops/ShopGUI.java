package com.sereneoasis.npc.guis.shops;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.sereneoasis.items.ItemStacks;
import com.sereneoasis.utils.EconUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ShopGUI {

    private ChestGui gui;

    public void openGUI(Player player) {
        gui.show(player);
    }

    public ShopGUI(Player player){
        gui = new ChestGui(6, "Shop");

        gui.setOnGlobalClick(event -> event.setCancelled(true));


        PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);
        pages.populateWithItemStacks(Arrays.stream(ItemStacks.values()).map(itemStacks -> itemStacks.getItemStack()).collect(Collectors.toList()));
        pages.setOnClick(event -> {
            if (ItemStacks.getByName(event.getCurrentItem().getItemMeta().getDisplayName()) != null) {
                int price = ItemStacks.getByName(event.getCurrentItem().getItemMeta().getDisplayName()).getSellPrice();
                if (EconUtils.withdrawPlayer(player, price)){
                    player.getInventory().addItem(event.getCurrentItem());
                }
            }
        });

        gui.addPane(pages);

        OutlinePane background = new OutlinePane(0, 5, 9, 1);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);
        background.setPriority(Pane.Priority.LOWEST);

        gui.addPane(background);

        StaticPane navigation = new StaticPane(0, 5, 9, 1);
        navigation.addItem(new GuiItem(new ItemStack(Material.RED_WOOL), event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);

                gui.update();
            }
        }), 0, 0);

        navigation.addItem(new GuiItem(new ItemStack(Material.GREEN_WOOL), event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);

                gui.update();
            }
        }), 8, 0);

        navigation.addItem(new GuiItem(new ItemStack(Material.BARRIER), event ->
                event.getWhoClicked().closeInventory()), 4, 0);

        gui.addPane(navigation);
    }
}
