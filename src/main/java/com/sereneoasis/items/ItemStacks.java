package com.sereneoasis.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public enum ItemStacks {


    INFUSED_GOLDEN_CARROT(Material.GOLDEN_CARROT, 16, ChatColor.GOLD + "Infused Golden Carrots", 0, 100),
    SLOW_ROASTED_CHICKEN(Material.COOKED_CHICKEN, 64, ChatColor.RED + "Slow Roasted Chicken", 0, 60),
    DOUBLE_CHOCOLATE_COOKIE(Material.COOKIE, 8, ChatColor.GREEN + "Double Chocolate Cookie", 0, 20);


    public static ItemStacks getByName(String displayName){
        Optional<ItemStacks> check = Arrays.stream(ItemStacks.values()).filter(itemStacks -> itemStacks.getItemStack().getItemMeta().getDisplayName().equals(displayName)).findAny();
        if (check.isPresent()){
            return  check.get();
        }
        return null;
    }

    private final ItemStack itemStack;

    public ItemStack getItemStack(){
        return this.itemStack;
    }

    private final int buyPrice, sellPrice;

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    ItemStacks(Material material, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        buyPrice = 0;
        sellPrice = 0;

    }

    ItemStacks(Material material, int amount, String displayName, int buyPrice, int sellPrice) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    ItemStacks(Material material, int amount, String displayName, List<String> lore, int buyPrice, int sellPrice) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
