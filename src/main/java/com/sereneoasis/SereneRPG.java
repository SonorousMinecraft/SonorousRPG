package com.sereneoasis;

import com.sereneoasis.config.FileManager;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
import com.sereneoasis.level.world.noise.NoiseMaster;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/***
 * The main class of the plugin
 */
public class SereneRPG extends JavaPlugin {

    public static SereneRPG plugin;

    private static FileManager fileManager;

    /***
     * Returns the class used to manage files
     * @return the file manager
     */
    public static FileManager getFileManager() {
        return fileManager;
    }


    @Override
    public void onEnable() {

        getLogger().log(Level.INFO, "WorldGenerator was enabled successfully.");
        plugin = this;
        fileManager = new FileManager();
        this.getServer().getPluginManager().registerEvents(new SereneListener(), this);

    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "WorldGenerator was disabled successfully.");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        BiomeRepresentation.initBiomes();
        NoiseMaster.initNoise();
        getLogger().log(Level.WARNING, "CustomChunkGenerator is used!");
        return new CustomChunkGenerator();
    }

}