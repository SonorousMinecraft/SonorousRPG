package com.sereneoasis.level.world.biome.biomes.flatland.plains;

import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SunflowerPlains extends BiomeRepresentation implements FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, List.of(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, List.of(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, List.of(Material.BEDROCK));
    }};
    public SunflowerPlains() {
        super(org.bukkit.block.Biome.SUNFLOWER_PLAINS, "Sunflower Plains", layers, 0.4, 0.4, 0.2, 0.1, BiomeCategories.FLAT);
    }

    @Override
    public HashMap<Material, Integer> getFlora() {
        HashMap<Material, Integer>flora = new HashMap<>();
        flora.put( Material.SUNFLOWER, 40);
        return flora;
    }
}

