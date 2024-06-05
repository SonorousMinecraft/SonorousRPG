package com.sereneoasis.level.world.biome.biomes.aridland.savanna;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SavannaPlateau extends BiomeRepresentation implements TreeBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.GRASS_BLOCK));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public SavannaPlateau() {
        super(org.bukkit.block.Biome.SAVANNA_PLATEAU, "Savanna Plateau", layers, 0.6, 0.8, -0.3);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.ACACIA};
    }
}

