package com.sereneoasis.player;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SpecialisationGUI {

    private ChestGui gui;

    public SpecialisationGUI(){
        gui  = new ChestGui(3, "Specialisation");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(0, 0, Specialisation.values().length, 1);

        Arrays.stream(Specialisation.values())
                .forEach(specialisation -> {
                    ItemStack symbol = specialisation.getSymbol();
                    ItemMeta symbolItemMeta = symbol.getItemMeta();
                    symbolItemMeta.setDisplayName(specialisation.getName());
                    symbol.setItemMeta(symbolItemMeta);

                    navigationPane.addItem(new GuiItem(symbol, event -> {
                        Player player = (Player) event.getWhoClicked();
                        SerenePlayer.getSerenePlayer(player).setSpecialisation(specialisation);
                        player.sendMessage("You have successfully chosen " + specialisation.getName() );
                        player.closeInventory();
                    }));
                });

        gui.addPane(navigationPane);
    }

    public void openGUI(Player player){
        gui.show(player);
    }

}
