package com.sereneoasis.level.world.chunk.populator;

import com.sereneoasis.level.world.biome.BiomeRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TreePopulator extends BlockPopulator {

    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        int x = random.nextInt(16) + chunkX * 16;
        int z = random.nextInt(16) + chunkZ * 16;
        int y = 319;
        while(limitedRegion.getType(x, y, z).isAir() && y > -64) y--;

        Location location = new Location(Bukkit.getWorld(worldInfo.getUID()), x, y, z);
        Biome biome = limitedRegion.getBiome(location);
        if (BiomeRepresentation.isTreeBiome(biome)){
            if (limitedRegion.getType(x, y - 1, z).isSolid()){
                limitedRegion.generateTree(location, random, BiomeRepresentation.getTreeTypes(biome).get(random.nextInt(BiomeRepresentation.getTreeTypes(biome).size())));
            }
        }
    }
}
