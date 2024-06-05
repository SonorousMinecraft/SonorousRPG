package com.sereneoasis.level.world.biome.biomes.offland.oceans.cold;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DeepFrozenOcean extends BiomeRepresentation {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.DIRT));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public DeepFrozenOcean() {
        super(org.bukkit.block.Biome.DEEP_FROZEN_OCEAN, "Deep Frozen Ocean", layers, -1.0, -1.0, -0.7);
    }
}