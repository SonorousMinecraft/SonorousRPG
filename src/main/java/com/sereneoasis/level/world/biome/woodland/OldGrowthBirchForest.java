package com.sereneoasis.level.world.biome.woodland;

import com.sereneoasis.level.world.biome.Biome;
import com.sereneoasis.level.world.biome.BiomeLayers;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OldGrowthBirchForest extends Biome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public OldGrowthBirchForest() {
        super(org.bukkit.block.Biome.OLD_GROWTH_BIRCH_FOREST, "Old Growth Birch Forest", layers, 0.2, 0.4, 0);
    }
}

