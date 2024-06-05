package com.sereneoasis.level.world.biome.biomes.woodland.taiga;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OldGrowthPineTaiga extends BiomeRepresentation implements TreeBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public OldGrowthPineTaiga() {
        super(org.bukkit.block.Biome.OLD_GROWTH_PINE_TAIGA, "Old Growth Pine Taiga", layers, 0.1, 0.5, 0);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.REDWOOD, TreeType.TALL_REDWOOD};
    }
}

