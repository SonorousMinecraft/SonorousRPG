package com.sereneoasis.level.world.noise;

import com.sereneoasis.libs.FastNoiseLite;

import java.util.HashMap;

public class GenerationNoise {

    private static final HashMap<NoiseTypes, FastNoiseLite> NOISE_TYPE_FUNCTION_MAP = new HashMap<>();

    public static float getNoise(NoiseTypes noiseTypes, int x, int z){
        float noise = NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, z);
        switch (noiseTypes){
            case CONTINENTALNESS -> {
                return noise*1.7f;
            }
            case TERRAIN_NOISE -> {
                return noise;
            }
            case HUMIDITY -> {
                return noise*1.5f;
            }
            case TEMPERATURE -> {
                return noise*1.5f;
            }
            case WETLAND -> {
                return noise;
            }
            default -> {
                return noise;
            }
        }
    }

    public static float getNoise(NoiseTypes noiseTypes, int x, int y, int z){
        return NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, y, z) ;
    }

    private final FastNoiseLite noise;

    public GenerationNoise(float frequency, int octaves, NoiseTypes noiseTypes){
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(frequency);
        noise.SetFractalOctaves(octaves);
        noise.SetFractalType(FastNoiseLite.FractalType.FBm);

        NOISE_TYPE_FUNCTION_MAP.put(noiseTypes, noise);
    }

    public GenerationNoise(){
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.005f);
        noise.SetFractalOctaves(3);
        noise.SetFractalType(FastNoiseLite.FractalType.PingPong);
        noise.SetFractalLacunarity(0.5f);
        noise.SetFractalGain(10f);
        noise.SetFractalPingPongStrength(1.0f);

        noise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        noise.SetDomainWarpAmp(1.0f);

        NOISE_TYPE_FUNCTION_MAP.put(NoiseTypes.WETLAND, noise);
    }

}
