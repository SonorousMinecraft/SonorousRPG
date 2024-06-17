package com.sereneoasis.npc.guis.quests;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestGUI {

    private ChestGui gui;

    private OutlinePane itemPane;

    public void openGUI(Player player) {
        gui.show(player);
    }

    public QuestGUI(Player player){
        gui = new ChestGui(3, "Select amount");

        gui.setOnGlobalClick(event -> event.setCancelled(true));

//        ItemStack item = new ItemStack(Material.DIAMOND);

        itemPane = new OutlinePane(0, 0, 9, 5);
//        itemPane.addItem(new GuiItem(item));

//        Label decrement = new Label(2, 1, 1, 1, Font.OAK_PLANKS);
//        decrement.setText("-");
//        decrement.setVisible(false);
//
//        Label increment = new Label(6, 1, 1, 1, Font.OAK_PLANKS);
//        increment.setText("+");
//
//        if (item.getMaxStackSize() == 1) {
//            increment.setVisible(false);
//        }
//
//        decrement.setOnClick(event -> {
//            item.setAmount(item.getAmount() - 1);
//
//            if (item.getAmount() == 1) {
//                decrement.setVisible(false);
//            }
//
//            increment.setVisible(true);
//
//            gui.update();
//        });
//
//        increment.setOnClick(event -> {
//            item.setAmount(item.getAmount() + 1);
//
//            decrement.setVisible(true);
//
//            if (item.getAmount() == item.getMaxStackSize()) {
//                increment.setVisible(false);
//            }
//
//            gui.update();
//        });

        gui.addPane(itemPane);
//        gui.addPane(decrement);
//        gui.addPane(increment);
    }

    public void addAttainmentQuest(ItemStack required, ItemStack reward){
        GuiItem rewardItem = new GuiItem(reward);
        itemPane.addItem(rewardItem);
        rewardItem.setAction(inventoryClickEvent -> {
            if (inventoryClickEvent.isRightClick()){
                Inventory inv = inventoryClickEvent.getWhoClicked().getInventory();
                if (inv.containsAtLeast(required, 1)){
                    ItemStack singularRequired = required.clone();
                    singularRequired.setAmount(1);
                    inv.remove(singularRequired);
                    if (inventoryClickEvent.getCurrentItem().getAmount() == 1){
                        inventoryClickEvent.getWhoClicked().getInventory().addItem(reward);
                    }
                    inventoryClickEvent.getCurrentItem().setAmount(inventoryClickEvent.getCurrentItem().getAmount() - 1);

                }
            }
        });
    }
}
