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


    INFUSED_GOLDEN_CARROT(Material.GOLDEN_CARROT, ItemCategory.GOD_FOOD, 16, ChatColor.GOLD + "Infused Golden Carrots", 0, 100),
    NOTCH_APPLE(Material.GOLDEN_APPLE, ItemCategory.GOD_FOOD, 16, ChatColor.GOLD + "Notch Apple", 0, 100),
    SLOW_ROASTED_CHICKEN(Material.COOKED_CHICKEN,ItemCategory.MEAT, 64, ChatColor.RED + "Slow Roasted Chicken", 0, 60),
    SIRLOIN_STEAK(Material.COOKED_BEEF, ItemCategory.MEAT,64, ChatColor.RED + "Sirloin Steak", 0, 60),
    LAMB_CHOPS(Material.COOKED_MUTTON, ItemCategory.MEAT, 64, ChatColor.RED + "Lamb Chops", 0, 60),
    PAN_SEARED_PORK_CHOPS(Material.COOKED_PORKCHOP, ItemCategory.MEAT, 64, ChatColor.RED + "Pan Seared Pork Chops", 0, 60),
    BRAISED_RABBIT(Material.COOKED_RABBIT, ItemCategory.VEGETABLES, 64, ChatColor.RED + "Braised Rabbit", 0, 60),
    DOUBLE_CHOCOLATE_COOKIE(Material.COOKIE,  ItemCategory.BAKED_FOOD, 8, ChatColor.DARK_RED + "Double Chocolate Cookie", 0, 20),
    BAKERS_LOAVES(Material.BREAD,  ItemCategory.BAKED_FOOD, 8, ChatColor.DARK_RED + "Baker's Loaves", 0, 100),
    RED_VELVET_CAKE(Material.BREAD,  ItemCategory.BAKED_FOOD, 1, ChatColor.DARK_RED + "Red Velvet Cake", 0, 200);


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
    
    private ItemCategory category;
    
    

    ItemStacks(Material material, ItemCategory category, int amount, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount);
        this.category = category;
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        buyPrice = 0;
        sellPrice = 0;

    }

    ItemStacks(Material material, ItemCategory category, int amount, String displayName, int buyPrice, int sellPrice) {
        ItemStack itemStack = new ItemStack(material, amount);
        this.category = category;

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    ItemStacks(Material material, ItemCategory category, int amount, String displayName, List<String> lore, int buyPrice, int sellPrice) {
        ItemStack itemStack = new ItemStack(material, amount);
        this.category = category;

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
