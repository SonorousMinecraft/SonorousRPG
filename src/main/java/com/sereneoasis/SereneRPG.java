package com.sereneoasis;

import com.sereneoasis.command.SerenityCommand;
import com.sereneoasis.config.FileManager;
import com.sereneoasis.level.world.biome.BiomeRepresentation;
import com.sereneoasis.level.world.chunk.CustomChunkGenerator;
import com.sereneoasis.level.world.noise.NoiseMaster;
import com.sereneoasis.listeners.PacketListener;
import com.sereneoasis.listeners.SereneListener;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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

    private static PacketListener packetListener;

    public static PacketListener getPacketListener() {
        return packetListener;
    }

    //Used to keep our NPCs to be accessed in other classes
    private Set<ServerPlayer> npcs = new HashSet<>();
    public Set<ServerPlayer> getNpcs() {
        return npcs;
    }

    @Override
    public void onEnable() {

        getLogger().log(Level.INFO, "WorldGenerator was enabled successfully.");
        plugin = this;
        fileManager = new FileManager();
        this.getServer().getPluginManager().registerEvents(new SereneListener(), this);
        this.getCommand("sereneRPG").setExecutor(new SerenityCommand());

        packetListener = new PacketListener();
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