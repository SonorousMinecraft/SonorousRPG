package com.sereneoasis.level.world.noise;

import com.mojang.datafixers.util.Pair;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.io.Serializable;
import java.util.*;
import java.util.function.ToDoubleFunction;

public class NoiseMaster {

    public NoiseMaster(){

            new GenerationNoise(0.0001f, 2, NoiseTypes.TERRAIN_NOISE);

            new GenerationNoise(0.0002f, 1, NoiseTypes.CONTINENTALNESS);
            new GenerationNoise(0.001f, 2, NoiseTypes.TEMPERATURE);
            new GenerationNoise(0.001f, 2, NoiseTypes.HUMIDITY);

            new GenerationNoise(0.01f, 1, NoiseTypes.DETAIl);
            new GenerationNoise(0.001f, 1, NoiseTypes.WEIRDNESS);
            new GenerationNoise();
            new GenerationNoise(0.01F, 3, NoiseTypes.CAVES);
            new GenerationNoise(0.05F, 2, NoiseTypes.FLORA);
    }

    public static float getMasterNoise(int chunkX, int chunkZ, int x, int z) {
        float continentalWeight = 10;
        float terrainWeight = 2;
        float detailWeight = 0.5f;

        float uncappedNoise = (GenerationNoise.getNoise(NoiseTypes.CONTINENTALNESS, x + (chunkX * 16), z + (chunkZ * 16)) * continentalWeight) +
                ( GenerationNoise.getNoise(NoiseTypes.TERRAIN_NOISE, x + (chunkX * 16), z + (chunkZ * 16)) * terrainWeight  )
                + (GenerationNoise.getNoise(NoiseTypes.DETAIl, x + (chunkX * 16), z + (chunkZ * 16)) * detailWeight);
        return uncappedNoise / ((continentalWeight + terrainWeight + detailWeight)/4);
//        if (uncappedNoise < 0){
//            return Math.max(-1, uncappedNoise);
//        } else {
//            return Math.min(1, uncappedNoise);
//        }
    }


    private static BiomeRepresentation getBiomeRepresentation(int x, int z){
        double targetContinentalness = GenerationNoise.getNoise(NoiseTypes.CONTINENTALNESS, x, z) ;
        double targetTemeprature = GenerationNoise.getNoise(NoiseTypes.TEMPERATURE, x, z) ;
        double targetHumidity = GenerationNoise.getNoise(NoiseTypes.HUMIDITY, x, z) ;
        double weirdness = GenerationNoise.getNoise(NoiseTypes.WEIRDNESS, x ,z) ;

        BiomeCategories category = null;

        if (GenerationNoise.getNoise(NoiseTypes.WETLAND, x ,z ) > 0.65 && targetContinentalness >= -0.1 ) {
            category = BiomeCategories.WET;
        } else {

            if (targetContinentalness <= -0.2) { // offland
                category = BiomeCategories.OFF;
            } else if (targetContinentalness > -0.2 && targetContinentalness <= -0.1) { // coastal
                category = BiomeCategories.COASTAL;
            } else if (targetContinentalness > -0.1 && targetContinentalness <= 0.1) { // flatland
                category = BiomeCategories.FLAT;

            } else if (targetContinentalness > 0.1 && targetContinentalness <= 0.25) { // woodland
                category = BiomeCategories.WOOD;

            } else if (targetContinentalness > 0.25 && targetContinentalness <= 0.4) { // aridland
                category = BiomeCategories.ARID;

            } else { // highland
                category = BiomeCategories.HIGH;

            }
        }

        return BiomeRepresentation.getBiomeRepresentations(category)
                .stream()
                .map(biomeRepresentation -> {
                    return Pair.of(biomeRepresentation, (Math.abs(biomeRepresentation.getContinentalness() - targetContinentalness) )
                            + (Math.abs(biomeRepresentation.getHumidity() - targetHumidity) )
                            + (Math.abs(biomeRepresentation.getTemperature() - targetTemeprature) )
                            + (Math.abs(weirdness * (biomeRepresentation.getWeirdness() +  weirdness) )));
                })
                .reduce((biomeRepresentationDoublePair, biomeRepresentationDoublePair2) -> {
                    if (biomeRepresentationDoublePair.getSecond() < biomeRepresentationDoublePair2.getSecond()) {
                        return biomeRepresentationDoublePair;
                    }
                    return biomeRepresentationDoublePair2;
                })
                .get().getFirst();
    }


    public static Biome getBiome(int x, int z){
       return getBiomeRepresentation(x, z).getBiome();
    }

    public static HashMap<BiomeLayers, List<Material>> getBiomeLayers(int x, int z){
        return getBiomeRepresentation(x, z).getLayers();
    }

    public static float getCaveNoise(int chunkX, int chunkZ, int x, int y, int z){
        return GenerationNoise.getNoise(NoiseTypes.CAVES, chunkX * 16 + x, y, chunkZ * 16 + z);
    }

    public static float getFloraNoise(int chunkX, int chunkZ, int x, int z){
        return GenerationNoise.getNoise(NoiseTypes.FLORA, chunkX * 16 + x, chunkZ * 16 + z);
    }

}
