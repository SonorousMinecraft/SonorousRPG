package com.sereneoasis.level.world.biome.biomefeatures;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.Arrays;
import java.util.HashMap;

public class FloraBiomeUtils {

    private static final Material[] smallFlowers = new Material[]{Material.POPPY, Material.LILY_OF_THE_VALLEY, Material.ALLIUM,
            Material.DANDELION, Material.AZURE_BLUET, Material.ORANGE_TULIP, Material.PINK_TULIP, Material.RED_TULIP,
            Material.WHITE_TULIP, Material.TORCHFLOWER, Material.BLUE_ORCHID, Material.OXEYE_DAISY};

    public static HashMap<Integer, Material> getSmallFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        Arrays.stream(smallFlowers).forEach(material -> {
            flora.put(amount, material);
        });
        return flora;
    }


    private static final Material[] tallFlowers = new Material[]{Material.ROSE_BUSH, Material.LILAC, Material.PEONY, Material.SUNFLOWER};


    public static HashMap<Integer, Material> getTallFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        Arrays.stream(tallFlowers).forEach(material -> {
            flora.put(amount, material);
        });
        return flora;
    }

    public static HashMap<Integer, Material> getFlowers(int amount) {
        HashMap<Integer, Material>flora = new HashMap<>();
        flora.putAll(getSmallFlowers(amount));
        flora.putAll(getTallFlowers(amount));
        return flora;
    }
}
