package com.sereneoasis.level.world.biome.biomes.woodland.forest;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiomeUtils;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OldGrowthBirchForest extends BiomeRepresentation implements TreeBiome, FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public OldGrowthBirchForest() {
        super(org.bukkit.block.Biome.OLD_GROWTH_BIRCH_FOREST, "Old Growth Birch Forest", layers, 0.2, 0.4, 0, 0.1);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.BIRCH, TreeType.TALL_BIRCH};
    }


    @Override
    public HashMap<Integer, Material> getFlora() {
        HashMap<Integer, Material>flora = new HashMap<>();
        flora.put(10, Material.SHORT_GRASS);
        flora.put(5, Material.TALL_GRASS);
        flora.put(5, Material.SWEET_BERRY_BUSH);
        flora.putAll(FloraBiomeUtils.getFlowers(10));

        return flora;
    }
}

