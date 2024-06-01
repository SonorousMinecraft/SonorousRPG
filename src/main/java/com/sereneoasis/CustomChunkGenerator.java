package com.sereneoasis;

import com.sereneoasis.libs.FastNoiseLite;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import java.util.Random;

public class CustomChunkGenerator extends ChunkGenerator {
    private final FastNoiseLite terrainNoise = new FastNoiseLite();
    private final FastNoiseLite detailNoise = new FastNoiseLite();

    public CustomChunkGenerator() {
        // Set frequencies, lower frequency = slower change.
        terrainNoise.SetFrequency(0.001f);
        detailNoise.SetFrequency(0.05f);

        // Fractal pattern (optional).
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(5);
    }

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < 130 && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    float noise2 = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float noise3 = detailNoise.GetNoise(x + (chunkX * 16), y, z + (chunkZ * 16));
                    float currentY = (65 + (noise2 * 30));

                    if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.
                        double function = .1 * Math.pow(distanceToSurface, 2) - 1; // A second grade polynomial offset to the noise max and min (1, -1).

                        if(noise3 > Math.min(function, -.3)) {
                            chunkData.setBlock(x, y, z, Material.STONE);
                        }
                    }
                    else if(y < 62) {
                        chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
    }
}