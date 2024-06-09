package com.sereneoasis.level.world.chunk.populator;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Vector;

public class FeaturePopulator extends BlockPopulator {

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        Biome biome = limitedRegion.getBiome(chunkX * 16,0,chunkZ * 16);
        if (BiomeRepresentation.isFeatureBiome(biome)) {
            BiomeRepresentation.getBiomeFeatures(biome).forEach((feature, aDouble) -> {
                if (aDouble > random.nextDouble()){
                    int x = random.nextInt(16) + chunkX * 16;
                    int z = random.nextInt(16) + chunkZ * 16;

                    int y = 319;
                    while (limitedRegion.getType(x, y, z).isAir() && y > -64) y--;


                    if (limitedRegion.getType(x, y, z).equals(Material.GRASS_BLOCK)) {


                        int finalY = y;
                        feature.getVectorMaterialHashMap().forEach((vector, material) -> {
                            limitedRegion.setType((int) (x + vector.getX()), (int) (finalY + vector.getY()), (int) (z + vector.getZ()), material);

                        });
                    }
                }
            });
        }
    }
}
