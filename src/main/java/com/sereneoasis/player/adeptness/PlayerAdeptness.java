package com.sereneoasis.player.adeptness;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum PlayerAdeptness {

    UNARMED("Unarmed", new ItemStack(Material.BARRIER)),
    SWORDS("Swords", new ItemStack(Material.WOODEN_SWORD)),
    MINING("Mining", new ItemStack(Material.WOODEN_PICKAXE)),
    MOVEMENT("Movement", new ItemStack(Material.LEATHER_BOOTS)),

    TOOLS("Tools", new ItemStack(Material.WOODEN_PICKAXE)),
    RANGED("Ranged", new ItemStack(Material.BOW));

    public static PlayerAdeptness getFromName(String name){
        if (Arrays.stream(values()).filter(specialisation -> specialisation.getName().equals(name)).findAny().isPresent()) {
            return Arrays.stream(values()).filter(specialisation -> specialisation.getName().equals(name)).findAny().get();
        }
        return null;
    }

    private final String name;

    private final ItemStack symbol;

    public String getName(){
        return name;
    }

    public ItemStack getSymbol() {
        return symbol;
    }

    PlayerAdeptness(String name, ItemStack symbol){
        this.name = name;
        this.symbol = symbol;
    }
}
