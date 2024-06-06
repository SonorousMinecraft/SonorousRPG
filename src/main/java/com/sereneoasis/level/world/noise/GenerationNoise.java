package com.sereneoasis.level.world.noise;

import com.sereneoasis.libs.FastNoiseLite;

import java.util.HashMap;

public class GenerationNoise {

    private static final HashMap<NoiseTypes, FastNoiseLite> NOISE_TYPE_FUNCTION_MAP = new HashMap<>();

    public static float getNoise(NoiseTypes noiseTypes, int x, int z){
        return NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, z);
    }

    public static float getNoise(NoiseTypes noiseTypes, int x, int y, int z){
        return NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, y, z);
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

}
