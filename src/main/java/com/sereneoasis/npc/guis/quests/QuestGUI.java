package com.sereneoasis.npc.guis.quests;

import com.github.stefvanschie.inventoryframework.font.util.Font;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.component.Label;
import net.minecraft.world.entity.MobType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

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

    private static final HashMap<UUID, Pair<EntityType, Integer>> HUNT_KILL_TRACKER = new HashMap<>();

    public static void decrementHuntKilLTracker(Player killer, LivingEntity livingEntity){
        if (HUNT_KILL_TRACKER.containsKey(killer.getUniqueId())){
            if (HUNT_KILL_TRACKER.get(killer.getUniqueId()).getA().equals(livingEntity.getType())) {
                int newKillsLeft = HUNT_KILL_TRACKER.get(killer.getUniqueId()).getB() - 1;
                HUNT_KILL_TRACKER.put(killer.getUniqueId(), new Pair<>(livingEntity.getType(), newKillsLeft));
                Bukkit.broadcastMessage("You're making progress !");
            }
        }
    }

    public void addHuntQuest(ItemStack reward, EntityType entityType, int amount){
        GuiItem rewardItem = new GuiItem(reward);
        itemPane.addItem(rewardItem);
        rewardItem.setAction(inventoryClickEvent -> {
            if (inventoryClickEvent.isRightClick()) {
                if (inventoryClickEvent.getWhoClicked() instanceof Player player) {
                    if (!HUNT_KILL_TRACKER.containsKey(player.getUniqueId())) {
                        Bukkit.broadcastMessage("You have accepted the quest!");

                        HUNT_KILL_TRACKER.put(player.getUniqueId(), new Pair<>(entityType, amount));
                    } else if (HUNT_KILL_TRACKER.get(player.getUniqueId()).getA().equals(entityType)) {
                        int newKillsLeft = HUNT_KILL_TRACKER.get(player.getUniqueId()).getB();
                        if (newKillsLeft <= 0) {
                            player.getInventory().addItem(reward);
                            inventoryClickEvent.getCurrentItem().setAmount(0);
                        }
                    }
                }
            }
            });
    }
}




























