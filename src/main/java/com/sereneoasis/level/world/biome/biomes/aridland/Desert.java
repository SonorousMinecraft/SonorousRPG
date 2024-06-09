package com.sereneoasis.level.world.biome.biomes.aridland;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Desert extends BiomeRepresentation implements FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.SAND));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.SANDSTONE));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public Desert() {
        super(org.bukkit.block.Biome.DESERT, "Desert", layers, 0.7, 0.5, 0.2, BiomeCategories.ARID);
    }

    @Override
    public HashMap<Material, Integer> getFlora() {
        HashMap<Material, Integer>flora = new HashMap<>();
        flora.put(Material.CACTUS, 5);
        flora.put(Material.DEAD_BUSH, 5);
        return flora;
    }
}

