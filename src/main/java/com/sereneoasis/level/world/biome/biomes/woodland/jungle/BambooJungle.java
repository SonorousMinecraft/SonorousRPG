package com.sereneoasis.level.world.biome.biomes.woodland.jungle;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BambooJungle extends BiomeRepresentation implements FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public BambooJungle() {
        super(org.bukkit.block.Biome.BAMBOO_JUNGLE, "Bamboo Jungle", layers, -0.5, 0.3, 0.6, 0.1);
    }

    @Override
    public HashMap<Integer, Material> getFlora() {
        HashMap<Integer, Material>flora = new HashMap<>();
        flora.put(10, Material.SHORT_GRASS);
        flora.put(5, Material.TALL_GRASS);
        flora.put(5, Material.BAMBOO);

        return flora;
    }
}

