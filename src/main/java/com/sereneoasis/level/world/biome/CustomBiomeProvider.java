package com.sereneoasis.level.world.biome;

import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
import com.sereneoasis.level.world.noise.NoiseMaster;
import com.sereneoasis.libs.FastNoiseLite;
import net.minecraft.resources.ResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeParameterPoint;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomBiomeProvider extends BiomeProvider {

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        return NoiseMaster.getBiome(x, z);
//        return Biome.PLAINS;
    }

    @NotNull
    @Override
    public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return BiomeRepresentation.getValidBiomes().stream().toList();
    }
}
