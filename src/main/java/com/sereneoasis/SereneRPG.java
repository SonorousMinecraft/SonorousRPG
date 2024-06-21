package com.sereneoasis;

import com.sereneoasis.command.SerenityCommand;
import com.sereneoasis.config.FileManager;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
import com.sereneoasis.level.world.noise.NoiseMaster;
import com.sereneoasis.listeners.EnchantmentListener;
import com.sereneoasis.listeners.SereneListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.RegisteredServiceProvider;
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

    private static Economy econ = null;

    @Override
    public void onEnable() {

        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().log(Level.INFO, "WorldGenerator was enabled successfully.");
        plugin = this;
        fileManager = new FileManager();
        this.getServer().getPluginManager().registerEvents(new SereneListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
        this.getCommand("sereneRPG").setExecutor(new SerenityCommand());

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
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