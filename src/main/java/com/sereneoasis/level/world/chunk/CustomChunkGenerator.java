package com.sereneoasis.level.world.chunk;

import com.sereneoasis.level.world.chunk.populator.GrassPopulator;
import com.sereneoasis.level.world.chunk.populator.TreePopulator;
import com.sereneoasis.level.world.biome.CustomBiomeProvider;
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

    // For larger terrain changes or continentalness
    private static final FastNoiseLite terrainNoise = new FastNoiseLite();

    public static double getContinentalness(float x, float z){
        return terrainNoise.GetNoise(x, z);
    }

    // For larger terrain changes or continentalness
    private static final FastNoiseLite biomeSizeNoise = new FastNoiseLite();

    public static double getSize(float x, float z){
        return biomeSizeNoise.GetNoise(x, z);
    }

    // For details
    private static final FastNoiseLite detailNoise = new FastNoiseLite();
    
    private final int Y_LIMIT = 200, SEA_LEVEL = 50, LAYER_1_HEIGHT = 10, AVERAGE_HEIGHT = 100, DEVIATION = 50;

    private final HashMap<Biome, HashMap<Integer, List<Material>> > biomeLayers = new HashMap<>();

    private final HashMap<Integer, List<Material>> forestLayers = new HashMap<>() {{
        put(0, Arrays.asList(Material.PODZOL));
        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};

    private final HashMap<Integer, List<Material>> riverLayers = new HashMap<>() {{
        put(0, Arrays.asList(Material.ICE));
        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};

    private final HashMap<Integer, List<Material>> jungleLayers = new HashMap<>() {{
        put(0, Arrays.asList(Material.COARSE_DIRT));
        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};

    private final HashMap<Integer, List<Material>> oceanLayers = new HashMap<>() {{
        put(0, Arrays.asList(Material.LAPIS_BLOCK));
        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};

    private final HashMap<Integer, List<Material>> desertLayers = new HashMap<>() {{
        put(0, Arrays.asList(Material.SAND));
        put(1, Arrays.asList(Material.SANDSTONE));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};


    private final HashMap<Integer, List<Material>> plainsLayers = new HashMap<>() {{
        put(0, Arrays.asList( Material.GRASS_BLOCK));
        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
        put(3, Arrays.asList(Material.BEDROCK));
    }};



//    private final HashMap<Integer, List<Material>> layers = new HashMap<>() {{
//        put(0, Arrays.asList(Material.GRASS_BLOCK, Material.DIRT));
//        put(1, Arrays.asList(Material.DIRT, Material.COARSE_DIRT, Material.SAND, Material.GRAVEL));
//        put(2, Arrays.asList(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
//        put(3, Arrays.asList(Material.BEDROCK));
//    }};


    public CustomChunkGenerator() {
        // For biome generation specifically
        biomeSizeNoise.SetFrequency(0.0001f);
        biomeSizeNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        biomeSizeNoise.SetFractalOctaves(1);


        // Set frequencies
        terrainNoise.SetFrequency(0.001f);
        detailNoise.SetFrequency(0);

        // Add fractals
        terrainNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        terrainNoise.SetFractalOctaves(3);


        biomeLayers.put(Biome.FOREST, forestLayers);
        biomeLayers.put(Biome.RIVER, riverLayers);
        biomeLayers.put(Biome.JUNGLE, jungleLayers);
        biomeLayers.put(Biome.OCEAN, oceanLayers);
        biomeLayers.put(Biome.PLAINS, plainsLayers);
        biomeLayers.put(Biome.DESERT, desertLayers);
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    HashMap<Integer, List<Material>>layers = biomeLayers.get(Biome.PLAINS);
                    

                    float noise = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION)); // some threshold

                    if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.

                        // It is not the closest block to the surface but still very close.
                        if(distanceToSurface < LAYER_1_HEIGHT) {
                            chunkData.setBlock(x, y, z, layers.get(1).get(random.nextInt(layers.get(1).size())));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    HashMap<Integer, List<Material>>layers = biomeLayers.get(Biome.PLAINS);



                    float noise = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION));

                    if(y < 1) {
                        chunkData.setBlock(x, y, z, layers.get(3).get(random.nextInt(layers.get(3).size())));
                    }
                    else if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.

                        // Set grass if the block closest to the surface.
                        if(distanceToSurface < 1 && y > SEA_LEVEL) {

//                            WritableRegistry<Biome> registryWritable = (WritableRegistry<Biome>) dedicatedServer.registryAccess().registry(Registries.BIOME).get();
//                            ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new ResourceLocation(newBiomeName.toLowerCase()));
//                            Biome base = registryWritable.get(key);
//                            Holder<Biome> biomeHolder = Holder.direct(base);

                            chunkData.setBlock(x, y, z, layers.get(0).get(random.nextInt(layers.get(0).size())));
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
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    HashMap<Integer, List<Material>>layers = biomeLayers.get(Biome.PLAINS);

                    if(y < chunkData.getMinHeight() + 2) {
                        chunkData.setBlock(x, y, z, layers.get(3).get(random.nextInt(layers.get(3).size())));
                    }
                }
            }
        }
    }

    @Override
    public void generateCaves(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkGenerator.ChunkData chunkData) {
        for(int y = chunkData.getMinHeight(); y < Y_LIMIT && y < chunkData.getMaxHeight(); y++) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    HashMap<Integer, List<Material>>layers = biomeLayers.get(Biome.PLAINS);


                    float noise = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float currentY = (AVERAGE_HEIGHT + (noise * DEVIATION));

                    if(y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.
                        // Not close to the surface at all.
                        if (distanceToSurface > LAYER_1_HEIGHT) {
                            Material neighbour = Material.STONE;
                            List<Material> neighbourBlocks = new ArrayList<Material>(Arrays.asList(chunkData.getType(Math.max(x - 1, 0), y, z), chunkData.getType(x, Math.max(y - 1, 0), z), chunkData.getType(x, y, Math.max(z - 1, 0)))); // A list of all neighbour blocks.

                            // Randomly place vein anchors.
                            if (random.nextFloat() < 0.002) {
                                neighbour = layers.get(2).get(Math.min(random.nextInt(layers.get(2).size()), random.nextInt(layers.get(2).size()))); // A basic way to shift probability to lower values.
                            }

                            // If the current block has an ore block as neighbour, try the current block.
                            if ((!Collections.disjoint(neighbourBlocks, layers.get(2)))) {
                                for (Material neighbourBlock : neighbourBlocks) {
                                    if (layers.get(2).contains(neighbourBlock) && random.nextFloat() < -0.01 * layers.get(2).indexOf(neighbourBlock) + 0.4) {
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
        return super.shouldGenerateMobs();
    }
}

























