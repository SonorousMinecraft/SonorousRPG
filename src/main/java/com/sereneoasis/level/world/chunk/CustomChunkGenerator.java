package com.sereneoasis.level.world.chunk;

import com.sereneoasis.level.world.biome.BiomeLayers;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.chunk.populator.GrassPopulator;
import com.sereneoasis.level.world.chunk.populator.TreePopulator;
import com.sereneoasis.level.world.biome.CustomBiomeProvider;
import com.sereneoasis.level.world.noise.GenerationNoise;
import com.sereneoasis.level.world.noise.NoiseMaster;
import com.sereneoasis.level.world.noise.NoiseTypes;
import com.sereneoasis.libs.FastNoiseLite;
import net.minecraft.core.Holder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBiome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CustomChunkGenerator extends ChunkGenerator {

    private final int Y_LIMIT = 200, SEA_LEVEL = 50, LAYER_1_HEIGHT = 10, AVERAGE_HEIGHT = 100, DEVIATION = 50;

    public CustomChunkGenerator() {
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            HashMap<BiomeLayers, List<Material>>layers = NoiseMaster.getBiomeLayers(chunkX * 16, chunkZ * 16);

            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    float noise = NoiseMaster.getMasterNoise(chunkX, chunkZ, x, z);

                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION)); // some threshold

                    if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.

                        // It is not the closest block to the surface but still very close.
                        if(distanceToSurface < LAYER_1_HEIGHT) {
                            chunkData.setBlock(x, y, z, layers.get(BiomeLayers.PRIMARY).get(random.nextInt(layers.get(BiomeLayers.PRIMARY).size())));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            HashMap<BiomeLayers, List<Material>>layers = NoiseMaster.getBiomeLayers(chunkX * 16, chunkZ * 16);

            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {

                    float noise = NoiseMaster.getMasterNoise(chunkX, chunkZ, x, z);

                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION));

                    if(y < 1) {
                        chunkData.setBlock(x, y, z, layers.get(BiomeLayers.BASE).get(random.nextInt(layers.get(BiomeLayers.BASE).size())));
                    }
                    else if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.

                        // Set grass if the block closest to the surface.
                        if(distanceToSurface < 1 && y > SEA_LEVEL) {

//                            WritableRegistry<Biome> registryWritable = (WritableRegistry<Biome>) dedicatedServer.registryAccess().registry(Registries.BIOME).get();
//                            ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new ResourceLocation(newBiomeName.toLowerCase()));
//                            Biome base = registryWritable.get(key);
//                            Holder<Biome> biomeHolder = Holder.direct(base);

                            chunkData.setBlock(x, y, z, layers.get(BiomeLayers.SURFACE).get(random.nextInt(layers.get(BiomeLayers.SURFACE).size())));
                        }
                    }
                    else if(y < SEA_LEVEL) {
                        chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new CustomBiomeProvider();
    }

    @Override
    public void generateBedrock(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            HashMap<BiomeLayers, List<Material>>layers = NoiseMaster.getBiomeLayers(chunkX * 16, chunkZ * 16);

            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {

                    if(y < chunkData.getMinHeight() + 2) {
                        chunkData.setBlock(x, y, z, layers.get(BiomeLayers.BASE).get(random.nextInt(layers.get(BiomeLayers.BASE).size())));
                    }
                }
            }
        }
    }

    @Override
    public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            HashMap<BiomeLayers, List<Material>>layers = NoiseMaster.getBiomeLayers(chunkX * 16, chunkZ * 16);


            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {

                    float noise = NoiseMaster.getMasterNoise(chunkX, chunkZ, x, z);

                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION));

                    if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.
                        // Not close to the surface at all.
                        if (distanceToSurface > LAYER_1_HEIGHT) {
                            Material neighbour = Material.STONE;
                            List<Material> neighbourBlocks = new ArrayList<Material>(Arrays.asList(chunkData.getType(Math.max(x - 1, 0), y, z), chunkData.getType(x, Math.max(y - 1, 0), z), chunkData.getType(x, y, Math.max(z - 1, 0)))); // A list of all neighbour blocks.

                            // Randomly place vein anchors.
                            if (random.nextFloat() < 0.002) {
                                neighbour = layers.get(BiomeLayers.SECONDARY).get(Math.min(random.nextInt(layers.get(BiomeLayers.SECONDARY).size()), random.nextInt(layers.get(BiomeLayers.SECONDARY).size()))); // A basic way to shift probability to lower values.
                            }

                            // If the current block has an ore block as neighbour, try the current block.
                            if ((!Collections.disjoint(neighbourBlocks, layers.get(BiomeLayers.SECONDARY)))) {
                                for (Material neighbourBlock : neighbourBlocks) {
                                    if (layers.get(BiomeLayers.SECONDARY).contains(neighbourBlock) && random.nextFloat() < -0.01 * layers.get(BiomeLayers.SECONDARY).indexOf(neighbourBlock) + 0.4) {
                                        neighbour = neighbourBlock;
                                    }
                                }
                            }

                            chunkData.setBlock(x, y, z, neighbour);
                        }
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        return List.of(new GrassPopulator(), new TreePopulator());
    }

    @Override
    public boolean shouldGenerateMobs() {
        return false;
    }
}

























