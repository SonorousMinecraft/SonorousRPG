package com.sereneoasis.level.world.noise;

import com.mojang.datafixers.util.Pair;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class NoiseMaster {

    public NoiseMaster(){
        new GenerationNoise(0.00001f, 1, NoiseTypes.CONTINENTALNESS);

//        new GenerationNoise(0.01f, 3, NoiseTypes.CONTINENTALNESS);
        new GenerationNoise(0.0001f, 1, NoiseTypes.TEMPERATURE);
        new GenerationNoise(0.0001f, 1, NoiseTypes.HUMIDITY);

        new GenerationNoise(0.001f, 2, NoiseTypes.DETAIl);
    }

    public static float getMasterNoise(int chunkX, int chunkZ, int x, int z){
        return (GenerationNoise.getNoise(NoiseTypes.CONTINENTALNESS, x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (GenerationNoise.getNoise(NoiseTypes.DETAIl, x + (chunkX * 16), z + (chunkZ * 16)) / 10);
    }

    private static BiomeRepresentation getBiomeRepresentation(int x, int z){
        double targetContinentalness = GenerationNoise.getNoise(NoiseTypes.CONTINENTALNESS, x, z);
        double targetTemeprature = GenerationNoise.getNoise(NoiseTypes.TEMPERATURE, x, z);
        double targetHumidity = GenerationNoise.getNoise(NoiseTypes.HUMIDITY, x, z);

        return BiomeRepresentation.getBiomeRepresentations()
                .stream()
                .map(biomeRepresentation -> {
                    return Pair.of(biomeRepresentation, Math.abs(biomeRepresentation.getContinentalness() - targetContinentalness)
                            + Math.abs(biomeRepresentation.getHumidity() - targetHumidity)
                            + Math.abs(biomeRepresentation.getTemperature() - targetTemeprature));
                })
                .sorted(Comparator.comparingDouble(Pair::getSecond))
                .map(biomeRepresentationDoublePair -> biomeRepresentationDoublePair.getFirst())
                .findFirst()
                .get();
    }

    public static Biome getBiome(int x, int z){
       return getBiomeRepresentation(x, z).getBiome();
    }

    public static HashMap<BiomeLayers, List<Material>> getBiomeLayers(int x, int z){
        return getBiomeRepresentation(x, z).getLayers();
    }

}
