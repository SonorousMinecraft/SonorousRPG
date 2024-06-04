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
            List.of(Biome.OCEAN, Biome.DEEP_OCEAN, Biome.WARM_OCEAN, Biome.LUKEWARM_OCEAN, Biome.DEEP_LUKEWARM_OCEAN, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN, Biome.FROZEN_OCEAN, Biome.DEEP_FROZEN_OCEAN ), // offland
            List.of(Biome.PLAINS, Biome.SUNFLOWER_PLAINS, Biome.SNOWY_PLAINS, Biome.ICE_SPIKES), // flatland
            List.of(Biome.RIVER, Biome.FROZEN_RIVER, Biome.SWAMP, Biome.MANGROVE_SWAMP, Biome.BEACH, Biome.SNOWY_BEACH, Biome.STONY_SHORE), // wetland
            List.of(Biome.FOREST, Biome.FLOWER_FOREST, Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA, Biome.SNOWY_TAIGA, Biome.BIRCH_FOREST, Biome.OLD_GROWTH_BIRCH_FOREST, Biome.DARK_FOREST, Biome.JUNGLE, Biome.SPARSE_JUNGLE, Biome.BAMBOO_JUNGLE), // woodland
            List.of(Biome.DESERT, Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WINDSWEPT_SAVANNA, Biome.BADLANDS, Biome.WOODED_BADLANDS, Biome.ERODED_BADLANDS), // arid-land
            List.of(Biome.JAGGED_PEAKS, Biome.FROZEN_PEAKS, Biome.STONY_PEAKS, Biome.MEADOW, Biome.CHERRY_GROVE, Biome.GROVE, Biome.SNOWY_SLOPES, Biome.WINDSWEPT_HILLS, Biome.WINDSWEPT_GRAVELLY_HILLS, Biome.WINDSWEPT_FOREST)); // highland

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

    private static Biome getBiomeBasedOnSize(List<Biome> biomeList, int x, int z){
        double size = CustomChunkGenerator.getSize(x,z);
        int index = Math.max(0, Math.round(Math.round((size+1)* (biomeList.size()/2))) - 1);
        return biomeList.get(index);
    }

    @NotNull
    @Override
    public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        double continentalness = CustomChunkGenerator.getContinentalness(x,z);

//        if (continentalness > -1 && continentalness <= -0.6){ // offland
//
//            return biomes.get(0).get(random.nextInt(biomes.get(0).size()));
//        } else if (continentalness > -0.6 && continentalness <= -0.4) { // flatland
//            return biomes.get(1).get(random.nextInt(biomes.get(1).size()));
//
//        } else if (continentalness > -0.4 && continentalness <= -0.2) { // wetland
//            return biomes.get(2).get(random.nextInt(biomes.get(2).size()));
//
//        } else if (continentalness > -0.2 && continentalness <= 0.3) { // woodland
//            return biomes.get(3).get(random.nextInt(biomes.get(3).size()));
//
//        } else if (continentalness > 0.3 && continentalness <= 0.6) { // aridland
//            return biomes.get(4).get(random.nextInt(biomes.get(4).size()));
//
//        } else { // highland
//            return biomes.get(5).get(random.nextInt(biomes.get(5).size()));
//
//        }

        if (continentalness > -1 && continentalness <= -0.6){ // offland

            return getBiomeBasedOnSize(biomes.get(0), x, z);
        } else if (continentalness > -0.6 && continentalness <= -0.4) { // flatland
            return getBiomeBasedOnSize(biomes.get(1), x, z);

        } else if (continentalness > -0.4 && continentalness <= -0.2) { // wetland
            return getBiomeBasedOnSize(biomes.get(2), x, z);

        } else if (continentalness > -0.2 && continentalness <= 0.3) { // woodland
            return getBiomeBasedOnSize(biomes.get(3), x, z);

        } else if (continentalness > 0.3 && continentalness <= 0.6) { // aridland
            return getBiomeBasedOnSize(biomes.get(4), x, z);

        } else { // highland
            return getBiomeBasedOnSize(biomes.get(5), x, z);

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
