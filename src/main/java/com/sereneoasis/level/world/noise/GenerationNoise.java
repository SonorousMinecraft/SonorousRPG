package com.sereneoasis.level.world.noise;

import com.sereneoasis.libs.FastNoiseLite;

import java.util.HashMap;

/***
 * A Class used as a Wrapper with FastNoiseLite
 */
public class GenerationNoise {

    /***
     * A HashMap with NoiseTypes as keys and noise functions as values
     */
    private static final HashMap<NoiseTypes, FastNoiseLite> NOISE_TYPE_FUNCTION_MAP = new HashMap<>();

    /***
     * Retrieves the noise value for a given Location
     * @param noiseTypes the type of noise to retrieve the value for
     * @param x the X value of a location
     * @param z the Y value of a location
     * @return a value from -1 to 1 representing the noise, which may be stretched
     */
    public static float getNoise(NoiseTypes noiseTypes, int x, int z){
        float noise = NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, z);
        switch (noiseTypes){
            case CONTINENTALNESS -> {
                return noise*1.7f;
            }
            case HUMIDITY, TEMPERATURE -> {
                return noise*1.5f;
            }
            case CUSTOM_TREES -> {
                return noise *1.6f;
            }
            default -> {
                return noise;
            }
        }
    }

    /***
     * Retrieves the noise value for a given Location when the Y value is relevant
     * @param noiseTypes the type of noise to retrieve the value for
     * @param x the X value of a location
     * @param y the Y value of a location
     * @param z the Y value of a location
     * @return a value from -1 to 1 representing the noise
     */
    public static float getNoise(NoiseTypes noiseTypes, int x, int y, int z){
        return NOISE_TYPE_FUNCTION_MAP.get(noiseTypes).GetNoise(x, y, z) ;
    }

    private final FastNoiseLite noise;

    /***
     * Generates normal Perlin Noise
     * @param frequency
     * @param octaves
     * @param noiseTypes
     */
    public GenerationNoise(float frequency, int octaves, NoiseTypes noiseTypes){
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(frequency);
        noise.SetFractalOctaves(octaves);
        noise.SetFractalType(FastNoiseLite.FractalType.FBm);

        NOISE_TYPE_FUNCTION_MAP.put(noiseTypes, noise);
    }

    /***
     * Generates Snake-like noise
     */
    public GenerationNoise(float frequency, int octaves, NoiseTypes noiseTypes, float lacunarity, float gain, float pingPongStrength, float domainWarpAmp){
        noise = new FastNoiseLite();
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(frequency);
        noise.SetFractalOctaves(octaves);
        noise.SetFractalType(FastNoiseLite.FractalType.PingPong);
        noise.SetFractalLacunarity(lacunarity);
        noise.SetFractalGain(gain);
        noise.SetFractalPingPongStrength(pingPongStrength);

        noise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        noise.SetDomainWarpAmp(domainWarpAmp);

        NOISE_TYPE_FUNCTION_MAP.put(noiseTypes, noise);
    }

}
