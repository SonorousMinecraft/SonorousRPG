package com.sereneoasis;

import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;

public class SereneRPG extends JavaPlugin {

    public static SereneRPG plugin;


    public static void SereneRPG(String[] args) {
        System.out.println("Hello world!");
    }


    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "WorldGenerator was enabled successfully.");
        plugin = this;
        CustomBiome.addCustomBiome(SereneBiomeData.BADLANDS);
        this.getServer().getPluginManager().registerEvents(new SereneListener(), this);
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

    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
        return new CustomBiomeProvider();
    }
}