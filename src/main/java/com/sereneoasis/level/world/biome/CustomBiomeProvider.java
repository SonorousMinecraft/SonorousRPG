package com.sereneoasis.level.world.biome;

import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
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

    //https://minecraft.fandom.com/wiki/Biome
    private static final List<List<Biome>>biomes = List.of(
            List.of(Biome.OCEAN), // offland
            List.of(Biome.PLAINS), // flatland
            List.of(Biome.RIVER), // wetland
            List.of(Biome.FOREST, Biome.JUNGLE), // woodland
            List.of(Biome.DESERT), // arid-land
            List.of(Biome.JAGGED_PEAKS)); // highland

    private static final FastNoiseLite biomeChooser = new FastNoiseLite();

    private static final Random random = new Random();

    public static Biome getBiome(int x, int z){
        int index = Math.max(0, (int) Math.floor((biomeChooser.GetNoise( x , z) + 1)  * biomes.size()/2)  );
        return biomes.get(index).get(random.nextInt(biomes.get(index).size()));
    }

    public CustomBiomeProvider(){
        biomeChooser.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        biomeChooser.SetFractalOctaves(biomes.size());
        biomeChooser.SetFractalGain(0);
    }

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        double continentalness = CustomChunkGenerator.getContinentalness(x,z);

        if (continentalness > -1 && continentalness <= -0.2){ // offland
            return biomes.get(0).get(random.nextInt(biomes.get(0).size()));
        } else if (continentalness > -0.2 && continentalness <= 0) { // flatland
            return biomes.get(1).get(random.nextInt(biomes.get(1).size()));

        } else if (continentalness > 0 && continentalness <= 0.1) { // wetland
            return biomes.get(2).get(random.nextInt(biomes.get(2).size()));

        } else if (continentalness > 0.1 && continentalness <= 1.0) { // woodland
            return biomes.get(3).get(random.nextInt(biomes.get(3).size()));

        } else if (continentalness > 1.0 && continentalness <= 1.5) { // aridland
            return biomes.get(4).get(random.nextInt(biomes.get(4).size()));

        } else { // highland
            return biomes.get(5).get(random.nextInt(biomes.get(5).size()));

        }
//        return getBiome(x, z);
    }

//
//    @NotNull
//    @Override
//    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
//        return getBiome(x,z);
//    }

    @NotNull
    @Override
    public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return biomes.stream().reduce(new ArrayList<>(), (biomes1, biomes2) -> {
            biomes1.addAll(biomes2);
            return biomes1;
        });
    }
}
