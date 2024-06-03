package com.sereneoasis;

import com.sereneoasis.command.SerenityCommand;
import com.sereneoasis.level.world.biome.BiomeHelper;
import com.sereneoasis.level.world.biome.CustomBiome;
import com.sereneoasis.level.world.biome.CustomBiomeProvider;
import com.sereneoasis.level.world.biome.SereneBiomeData;
import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class SereneRPG extends JavaPlugin {

    public static SereneRPG plugin;


    public static void SereneRPG(String[] args) {
        System.out.println("Hello world!");
    }


    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "WorldGenerator was enabled successfully.");
        plugin = this;
        CustomBiome.addCustomBiome(SereneBiomeData.PLAINS);
        CustomBiome.addCustomBiome(SereneBiomeData.DESERT);
        CustomBiome.addCustomBiome(SereneBiomeData.JUNGLE);
        CustomBiome.addCustomBiome(SereneBiomeData.OCEAN);
        CustomBiome.addCustomBiome(SereneBiomeData.RIVER);
        CustomBiome.addCustomBiome(SereneBiomeData.FOREST);
        this.getServer().getPluginManager().registerEvents(new SereneListener(), this);
        this.getCommand("sereneRPG").setExecutor(new SerenityCommand());
        scheduleBiomeSwitching();
    }

    private Set<Chunk>changedChunks = new HashSet<>();

    private void scheduleBiomeSwitching(){
        Bukkit.getServer().getScheduler().runTaskLater(this, () -> {
            World world = Bukkit.getWorld("test");
            Set<Chunk> newChunks = Arrays.stream(world.getLoadedChunks()).filter(chunk -> !changedChunks.contains(chunk)).collect(Collectors.toSet());
            newChunks.forEach(chunk -> {
                        ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot(false, true, false);
                        switch (chunkSnapshot.getBiome(8, 130, 8)) {
                            case PLAINS -> BiomeHelper.setCustomBiome("serene_oasis:plains", chunk);
                            case FOREST -> BiomeHelper.setCustomBiome("serene_oasis:forest", chunk);
                            case RIVER -> BiomeHelper.setCustomBiome("serene_oasis:river", chunk);
                            case JUNGLE -> BiomeHelper.setCustomBiome("serene_oasis:jungle", chunk);
                            case OCEAN -> BiomeHelper.setCustomBiome("serene_oasis:ocean", chunk);
                            case DESERT -> BiomeHelper.setCustomBiome("serene_oasis:desert", chunk);
                        }
                    });
            changedChunks.addAll(newChunks);
            Bukkit.broadcastMessage("Switched biomes");
            scheduleBiomeSwitching();
        }, 100L);
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "WorldGenerator was disabled successfully.");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().log(Level.WARNING, "CustomChunkGenerator is used!");
        return new CustomChunkGenerator(); // Return an instance of the chunk generator we want to use.
    }

}