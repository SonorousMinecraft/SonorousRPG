package com.sereneoasis.level.world.chunk.populator;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BiomePopulator extends BlockPopulator {

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        for(int iteration = 0; iteration < 10; iteration++) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 319;


            while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;

            if(limitedRegion.getType(x, y, z).isSolid()) {

            }
        }
    }
}
