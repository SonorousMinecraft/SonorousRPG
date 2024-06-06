package com.sereneoasis.level.world.biome.biomes.wetland.swamp;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import org.bukkit.Material;
import org.bukkit.TreeType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MangroveSwamp extends BiomeRepresentation implements TreeBiome, FloraBiome {

    private static final HashMap<BiomeLayers, List<Material>> layers = new HashMap<>() {{
        put(BiomeLayers.SURFACE, Arrays.asList(Material.WATER, Material.MUD, Material.DIRT));
        put(BiomeLayers.PRIMARY, Arrays.asList(Material.DIRT, Material.MUD));
        put(BiomeLayers.SECONDARY, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(BiomeLayers.BASE, Arrays.asList(Material.BEDROCK));
    }};
    public MangroveSwamp() {
        super(org.bukkit.block.Biome.MANGROVE_SWAMP, "Mangrove Swamp", layers, 0.5, -0.2, 0.6);
    }

    @Override
    public TreeType[] getTreeType() {
        return new TreeType[]{TreeType.MANGROVE};
    }

    @Override
    public HashMap<Material, Integer> getFlora() {
        HashMap<Material, Integer>flora = new HashMap<>();
        flora.put(Material.SHORT_GRASS, 20);
        flora.put(Material.BIG_DRIPLEAF, 5);
        return flora;
    }

}
