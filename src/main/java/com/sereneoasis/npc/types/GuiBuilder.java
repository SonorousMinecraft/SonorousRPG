package com.sereneoasis.npc.types;

import com.sereneoasis.items.ItemStacks;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

public interface GuiBuilder {

    HashMap<ItemStack, ItemStack> getAttainmentQuests();
    HashMap<ItemStack, Pair<EntityType, Integer>> getHuntQuests();
    HashMap<ItemStack, Location> getExploreQuests();

    List<ItemStacks> getShopItems();

}
