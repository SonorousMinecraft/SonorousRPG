package com.sereneoasis.player.adeptness;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.specialisation.Specialisation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class AdeptnessGUI {

    private ChestGui gui;

    public AdeptnessGUI(Player player){
        gui  = new ChestGui(6, "Adeptness");
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(0, 0, Specialisation.values().length, 1);

        Arrays.stream(PlayerAdeptness.values())
                .forEach(adeptness -> {
                    ItemStack symbol = adeptness.getSymbol();
                    ItemMeta symbolItemMeta = symbol.getItemMeta();
                    symbolItemMeta.setDisplayName(adeptness.getName());
                    SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
                    int level = serenePlayer.getAdeptnessLevel(adeptness);
                    List<String> lore = List.of("Level: " + level);
                    symbolItemMeta.setLore(lore);
                    symbol.setItemMeta(symbolItemMeta);

                    navigationPane.addItem(new GuiItem(symbol, event -> {

                    }));
                });

        gui.addPane(navigationPane);
    }

    public void openGUI(Player player){
        gui.show(player);
    }
}
