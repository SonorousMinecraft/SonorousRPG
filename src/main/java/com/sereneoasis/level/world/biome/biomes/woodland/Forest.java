package com.sereneoasis.level.world.biome.biomes.woodland;

import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Forest extends BiomeRepresentation {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public Forest() {
        super(Biome.FOREST, "Forest", layers, 0.5, 0.4, 0);
    }
}
