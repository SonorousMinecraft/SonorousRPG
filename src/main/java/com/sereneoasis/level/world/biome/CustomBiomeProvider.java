package com.sereneoasis.level.world.biome;

import com.sereneoasis.libs.FastNoiseLite;
import net.minecraft.resources.ResourceKey;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class CustomBiomeProvider extends BiomeProvider {

    private static final List<Biome>biomes = List.of(Biome.FOREST, Biome.RIVER, Biome.JUNGLE, Biome.OCEAN, Biome.DESERT, Biome.PLAINS);

    private static final FastNoiseLite biomeChooser = new FastNoiseLite();

    public static Biome getBiome(int x, int z){
        int index = Math.max(0, (int) Math.floor((biomeChooser.GetNoise( x , z) + 1)  * biomes.size()/2)  );
        return biomes.get(index);
    }

    public CustomBiomeProvider(){
        biomeChooser.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        biomeChooser.SetFractalOctaves(biomes.size());
        biomeChooser.SetFractalGain(0);
    }

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        return getBiome(x,z);
    }

    @NotNull
    @Override
    public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return biomes;
    }
}
