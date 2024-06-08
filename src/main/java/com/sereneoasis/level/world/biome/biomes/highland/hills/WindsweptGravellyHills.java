package com.sereneoasis.level.world.biome.biomes.highland.hills;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WindsweptGravellyHills extends BiomeRepresentation {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRAVEL));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.GRAVEL));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public WindsweptGravellyHills() {
        super(org.bukkit.block.Biome.WINDSWEPT_GRAVELLY_HILLS, "Windswept Gravelly Hills", layers, -0.5, -0.7, -0.2, 0.5, BiomeCategories.HIGH);
    }
}

