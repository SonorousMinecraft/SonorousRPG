package com.sereneoasis.level.world.biome;

import net.minecraft.resources.ResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class CustomBiomeProvider extends BiomeProvider {

    private static final List<Biome>biomes = List.of(Biome.BADLANDS, Biome.FOREST, Biome.RIVER, Biome.JUNGLE, Biome.OCEAN, Biome.DESERT, Biome.PLAINS);
    private static final Random rand = new Random();

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        return biomes.get(rand.nextInt(0, biomes.size() -1));
    }

    @NotNull
    @Override
    public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return biomes;
    }
}
