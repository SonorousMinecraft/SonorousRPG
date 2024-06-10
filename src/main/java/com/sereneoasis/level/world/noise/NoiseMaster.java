package com.sereneoasis.level.world.noise;

import com.mojang.datafixers.util.Pair;
import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.biome.biomes.BiomeCategories;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.List;

public class NoiseMaster {

    public static void initNoise(){

            new GenerationNoise(0.0001f, 2, NoiseTypes.TERRAIN_NOISE);

            new GenerationNoise(0.0002f, 1, NoiseTypes.CONTINENTALNESS);
            new GenerationNoise(0.001f, 2, NoiseTypes.TEMPERATURE);
            new GenerationNoise(0.001f, 2, NoiseTypes.HUMIDITY);

            new GenerationNoise(0.01f, 1, NoiseTypes.DETAIl);
            new GenerationNoise(0.001f, 1, NoiseTypes.WEIRDNESS);
            new GenerationNoise(0.005f, 3, NoiseTypes.WETLAND, 0.5f, 10f, 1.0f, 1.0f);
            new GenerationNoise(0.01F, 3, NoiseTypes.CAVES);
            new GenerationNoise(0.05F, 2, NoiseTypes.FLORA);
    }

    /***
     * Calculates a score to represent what the height of the terrain should be at a given point
     * @param chunkX The value representing the Chunk X (it's actual X coordinate divided by 16)
     * @param chunkZ The value representing the Chunk Z (it's actual Z coordinate divided by 16)
     * @param x The X value relative to the chunk (from 0-15)
     * @param z The Z value relative to the chunk (from 0-15)
     * @return the noise at a given location used to calculate the Y position of the surface
     */
    public static float getMasterNoise(int chunkX, int chunkZ, int x, int z) {
        float continentalWeight = 10;
        float terrainWeight = 2;
        float detailWeight = 0.5f;

        float uncappedNoise = (GenerationNoise.getNoise(NoiseTypes.CONTINENTALNESS, x + (chunkX * 16), z + (chunkZ * 16)) * continentalWeight) +
                ( GenerationNoise.getNoise(NoiseTypes.TERRAIN_NOISE, x + (chunkX * 16), z + (chunkZ * 16)) * terrainWeight  )
                + (GenerationNoise.getNoise(NoiseTypes.DETAIl, x + (chunkX * 16), z + (chunkZ * 16)) * detailWeight);
        return uncappedNoise / ((continentalWeight + terrainWeight + detailWeight)/4);

    }


    /***
     * Calculates which biome representation represents a specified location
     * @param x the X of the Location we want to obtain the biome representation for
     * @param z the Z of the Location we want to obtain the biome representation for
     * @return A best fitting biome representation
     */
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
        // Below uses an algorithm to select which Biome out of the already chosen category is most appropriate.
        // There is a score given based on the difference between ideal characteristics which is meant to be minimised.
        // Weirdness is also taken into account to minimise the amount of more unusual biomes
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

    /***
     * Calculates which biome represents a specified location
     * @param x the X of the Location we want to obtain the biome for
     * @param z the Z of the Location we want to obtain the biome for
     * @return A best fitting biome
     */
    public static Biome getBiome(int x, int z){
       return getBiomeRepresentation(x, z).getBiome();
    }

    /***
     * Calculates what the layers of the terrain at a given location should be
     * @param x the X of the Location we want to obtain the Biome for
     * @param z the Z of the Location we want to obtain the Biome for
     * @return A best fitting Biome
     */
    public static HashMap<BiomeLayers, List<Material>> getBiomeLayers(int x, int z){
        return getBiomeRepresentation(x, z).getLayers();
    }

    /***
     * Obtains the noise used to generate caves
     * @param chunkX The value representing the Chunk X (it's actual X coordinate divided by 16)
     * @param chunkZ The value representing the Chunk Z (it's actual Z coordinate divided by 16)
     * @param x The X value relative to the chunk (from 0-15)
     * @param y The Y value relative to the chunk (from 0-15)
     * @param z The Z value relative to the chunk (from 0-15)
     * @return A value from -1 to 1 used to control cave generation
     */
    public static float getCaveNoise(int chunkX, int chunkZ, int x, int y, int z){
        return GenerationNoise.getNoise(NoiseTypes.CAVES, chunkX * 16 + x, y, chunkZ * 16 + z);
    }

    /***
     * Obtains the noise used to generate Flora
     * @param chunkX The value representing the Chunk X (it's actual X coordinate divided by 16)
     * @param chunkZ The value representing the Chunk Z (it's actual Z coordinate divided by 16)
     * @param x The X value relative to the chunk (from 0-15)
     * @param z The Z value relative to the chunk (from 0-15)
     * @return A value from -1 to 1 used to control flora generation
     */
    public static float getFloraNoise(int chunkX, int chunkZ, int x, int z){
        return GenerationNoise.getNoise(NoiseTypes.FLORA, chunkX * 16 + x, chunkZ * 16 + z);
    }

}
