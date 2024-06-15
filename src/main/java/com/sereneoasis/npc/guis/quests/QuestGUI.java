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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestGUI {

    private ChestGui gui;

    public void openGUI(Player player) {
        gui.show(player);
    }

    public QuestGUI(Player player){
        gui = new ChestGui(3, "Select amount");

        gui.setOnGlobalClick(event -> event.setCancelled(true));


        ItemStack item = new ItemStack(Material.DIAMOND);

        OutlinePane itemPane = new OutlinePane(4, 1, 1, 1);
        itemPane.addItem(new GuiItem(item));

        Label decrement = new Label(2, 1, 1, 1, Font.OAK_PLANKS);
        decrement.setText("-");
        decrement.setVisible(false);

        Label increment = new Label(6, 1, 1, 1, Font.OAK_PLANKS);
        increment.setText("+");

        if (item.getMaxStackSize() == 1) {
            increment.setVisible(false);
        }

        decrement.setOnClick(event -> {
            item.setAmount(item.getAmount() - 1);

            if (item.getAmount() == 1) {
                decrement.setVisible(false);
            }

            increment.setVisible(true);

            gui.update();
        });

        increment.setOnClick(event -> {
            item.setAmount(item.getAmount() + 1);

            decrement.setVisible(true);

            if (item.getAmount() == item.getMaxStackSize()) {
                increment.setVisible(false);
            }

            gui.update();
        });

        gui.addPane(itemPane);
        gui.addPane(decrement);
        gui.addPane(increment);
    }
}
