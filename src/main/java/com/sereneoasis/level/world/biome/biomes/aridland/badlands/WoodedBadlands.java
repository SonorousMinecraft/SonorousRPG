package com.sereneoasis.level.world.biome.biomes.aridland.badlands;

import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WoodedBadlands extends BiomeRepresentation {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, List.of(Material.TERRACOTTA));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.WHITE_TERRACOTTA, Material.GRAY_TERRACOTTA));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, List.of(Material.BEDROCK));
    }};
    public WoodedBadlands() {
        super(org.bukkit.block.Biome.WOODED_BADLANDS, "Wooded Badlands", layers, 1.0, 0.7, -0.9, 0.1, BiomeCategories.ARID);
    }
}

