package com.sereneoasis.level.world.biome.biomes.flatland;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IceSpikes extends BiomeRepresentation {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.BLUE_ICE));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.BLUE_ICE, Material.PACKED_ICE, Material.ICE));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public IceSpikes() {
        super(org.bukkit.block.Biome.ICE_SPIKES, "Ice Spikes", layers, -1.0, 0.4, 0, BiomeCategories.FLAT);
    }
}

