package com.sereneoasis.level.world.biome.biomes.highland.groves;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiomeUtils;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Grove extends BiomeRepresentation implements FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public Grove() {
        super(org.bukkit.block.Biome.GROVE, "Grove", layers, 0.2, 0.5, 0.1, BiomeCategories.HIGH);
    }

    @Override
    public HashMap<Material, Integer> getFlora() {
        HashMap<Material, Integer>flora = new HashMap<>();
        flora.put(Material.SHORT_GRASS, 20);
        flora.putAll(FloraBiomeUtils.getFlowers(10));

        return flora;
    }
}

