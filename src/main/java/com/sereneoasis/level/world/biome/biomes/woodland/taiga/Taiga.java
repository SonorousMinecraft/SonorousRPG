package com.sereneoasis.level.world.biome.biomes.woodland.taiga;

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

public class Taiga extends BiomeRepresentation implements TreeBiome, FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public Taiga() {
        super(org.bukkit.block.Biome.TAIGA, "Taiga", layers, -0.5, -0.8, 0);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.REDWOOD};
    }

    @Override
    public HashMap<Material, Integer> getFlora() {
        HashMap<Material, Integer>flora = new HashMap<>();
        flora.put(Material.SHORT_GRASS, 10);
        flora.put(Material.TALL_GRASS, 10);
        flora.putAll(FloraBiomeUtils.getFlowers(10));

        return flora;
    }
}

