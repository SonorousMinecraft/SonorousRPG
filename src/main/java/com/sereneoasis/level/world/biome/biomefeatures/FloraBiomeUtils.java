package com.sereneoasis.level.world.biome.biomefeatures;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.HashMap;

public class FloraBiomeUtils {


    // These are just lily of the vallies
    public static HashMap<Integer, Material> getSmallFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        Tag.SMALL_FLOWERS.getValues().forEach(material -> {
            flora.put(amount, material);
        });
        return flora;
    }

    // These are just pitcher plants
    public static HashMap<Integer, Material> getTallFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        Tag.TALL_FLOWERS.getValues().forEach(material -> {
            flora.put(amount, material);
        });
        return flora;
    }

    public static HashMap<Integer, Material> getFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        Tag.FLOWERS.getValues().forEach(material -> {
            flora.put(amount, material);
        });
        return flora;
    }
}
