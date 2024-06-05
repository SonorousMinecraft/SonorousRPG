package com.sereneoasis.level.world.biome.biomes.woodland.forest;

import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiomeUtils;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Forest extends BiomeRepresentation implements TreeBiome, FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public Forest() {
        super(Biome.FOREST, "Forest", layers, 0.5, 0.4, 0);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.TREE, TreeType.BIRCH};
    }


    @Override
    public HashMap<Integer, Material> getFlora() {
        HashMap<Integer, Material>flora = new HashMap<>();
        flora.put(10, Material.SHORT_GRASS);
        flora.put(5, Material.TALL_GRASS);
        flora.put(10, Material.SWEET_BERRY_BUSH);
        flora.putAll(FloraBiomeUtils.getFlowers(10));

        return flora;
    }
}
