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

public class AdeptnessPassiveGUI {

    private ChestGui gui;

    public AdeptnessPassiveGUI(SerenePlayer serenePlayer, PlayerAdeptness playerAdeptness){
        gui  = new ChestGui(6, "Adeptness passives : " + playerAdeptness.getName());
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        OutlinePane navigationPane = new OutlinePane(0, 0, AdeptnessPassivesManager.getAdeptnessPassives(playerAdeptness).size(), 1);

        AdeptnessPassivesManager.getAdeptnessPassives(playerAdeptness)
                .forEach(passive -> {

                    ItemStack symbol;
                    if (serenePlayer.getDisabledPassives().contains(passive)){
                        symbol = new ItemStack(Material.RED_STAINED_GLASS_PANE);

                    } else {
                        symbol = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                    }

                    ItemMeta symbolItemMeta = symbol.getItemMeta();

                    symbolItemMeta.setDisplayName(passive.getName());
                    List<String> lore = passive.getDescription();
                    lore.add(0,"Level: " + passive.getRequiredLevel());
                    symbolItemMeta.setLore(lore);
                    symbol.setItemMeta(symbolItemMeta);


                    navigationPane.addItem(new GuiItem(symbol, event -> {
                        if (serenePlayer.getDisabledPassives().contains(passive)){
                            serenePlayer.getDisabledPassives().remove(passive);
                            symbol.setType(Material.GREEN_STAINED_GLASS_PANE);
                            new AdeptnessPassiveGUI(serenePlayer, playerAdeptness).openGUI(serenePlayer.getPlayer());


                        } else {
                            symbol.setType(Material.RED_STAINED_GLASS_PANE);
                            serenePlayer.getDisabledPassives().add(passive);
                            new AdeptnessPassiveGUI(serenePlayer, playerAdeptness).openGUI(serenePlayer.getPlayer());

                        }
                    }));
                });

        gui.addPane(navigationPane);


    }

    public void openGUI(Player player){
        gui.show(player);
    }
}
