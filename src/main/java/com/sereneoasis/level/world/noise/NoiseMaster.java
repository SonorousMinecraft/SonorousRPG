package com.sereneoasis.level.world.noise;

import com.mojang.datafixers.util.Pair;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.io.Serializable;
import java.util.*;
import java.util.function.ToDoubleFunction;

public class NoiseMaster {

    public NoiseMaster(){
        boolean isTest = false;

        if (isTest){
            new GenerationNoise(0.001f, 2, NoiseTypes.TERRAIN_NOISE);

            new GenerationNoise(0.001f, 1, NoiseTypes.CONTINENTALNESS);
            new GenerationNoise(0.01f, 2, NoiseTypes.TEMPERATURE);
            new GenerationNoise(0.01f, 2, NoiseTypes.HUMIDITY);


            new GenerationNoise(0.01f, 1, NoiseTypes.DETAIl);
            new GenerationNoise(0.001f, 2, NoiseTypes.WEIRDNESS);

            new GenerationNoise(0.01F, 3, NoiseTypes.CAVES);
            new GenerationNoise(0.05F, 3, NoiseTypes.FLORA);

        } else {
            new GenerationNoise(0.0001f, 2, NoiseTypes.TERRAIN_NOISE);

            new GenerationNoise(0.0002f, 1, NoiseTypes.CONTINENTALNESS);
            new GenerationNoise(0.001f, 2, NoiseTypes.TEMPERATURE);
            new GenerationNoise(0.001f, 2, NoiseTypes.HUMIDITY);

            new GenerationNoise(0.01f, 1, NoiseTypes.DETAIl);
            new GenerationNoise(0.001f, 1, NoiseTypes.WEIRDNESS);
            new GenerationNoise(0.01F, 3, NoiseTypes.CAVES);
            new GenerationNoise(0.05F, 2, NoiseTypes.FLORA);

        }

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

        return BiomeRepresentation.getBiomeRepresentations()
                .stream()
                .map(biomeRepresentation -> {
                    return Pair.of(biomeRepresentation, (Math.abs(biomeRepresentation.getContinentalness() - targetContinentalness) * 20)
                            + (Math.abs(biomeRepresentation.getHumidity() - targetHumidity) * 5)
                            + (Math.abs(biomeRepresentation.getTemperature() - targetTemeprature) * 10)
                            + (Math.abs(weirdness * (biomeRepresentation.getWeirdness() +  weirdness) * 5)));
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
