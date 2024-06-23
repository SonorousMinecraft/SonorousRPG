package com.sereneoasis.player.specialisation;

import com.sereneoasis.items.ItemStacks;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum Specialisation {

    NONE("None", new ItemStack(Material.BARRIER)),
    FIGHTER("Fighter", ItemStacks.BASIC_SOLDIER_SWORD.getItemStack()),
    ROGUE("Rogue", ItemStacks.KUNAIS.getItemStack()),
    RANGER("Ranger", ItemStacks.WORN_BOW.getItemStack()),
    MAGE("Mage", ItemStacks.NOVICE_STAFF.getItemStack()),
    BARBARIAN("Barbarian", ItemStacks.BABY_BERSERKER_AXE.getItemStack()),
    ASSASSIN("Assassin", ItemStacks.BACKSTABBER.getItemStack());

    public static Specialisation getFromName(String name){
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

    Specialisation(String name, ItemStack symbol){
        this.name = name;
        this.symbol = symbol;
    }
}
