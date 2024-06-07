package com.sereneoasis;

import com.sereneoasis.level.world.Schematics;
import com.sereneoasis.level.world.biome.CustomBiomeProvider;
import com.sereneoasis.utils.StructureUtils;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_20_R3.block.CraftBiome;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class SereneListener implements Listener {

    private static final Random random = new Random();

//    @EventHandler
//    public void onChunkLoad(ChunkLoadEvent event){
//        if (random.nextDouble() < 0.001){
//
//            Chunk chunk = event.getChunk();
//
//            int snapshotX = random.nextInt(16);
//            int y = 256;
//            int snapshotZ = random.nextInt(16);
//
//            while(event.getChunk().getChunkSnapshot(false, false, false).getBlockType(snapshotX, y, snapshotZ).isAir() && y > -64) {
//                y--;
//                if (y == 0 ){
//                    return;
//                }
//            }
//            int x = snapshotX + chunk.getX() * 16;
//            int z = snapshotZ + chunk.getZ() * 16;
//            Location loc = new Location(event.getWorld(), x, y, z);
//            loc.setYaw(90 * random.nextInt(0, 4));
//            Schematics.pasteClipboard("fort1", loc);
//
//            Bukkit.getServer().getLogger().log(Level.INFO, loc.toString());
//
//
//        }
//    }

//    @EventHandler
//    public void onChunkLoad(ChunkLoadEvent event){
////        String key = event.getChunk().getWorld().getBiome(new Location(event.getWorld(), event.getChunk().getX(), 0, event.getChunk().getZ()) ).getKey().getKey();
//
//        Chunk chunk = event.getChunk();
//        ChunkSnapshot chunkSnapshot = chunk.getChunkSnapshot(false, true, false);
//        int chunkX = chunkSnapshot.getX();
//        int chunkZ = chunkSnapshot.getZ();
//        if (chunkSnapshot.getWorldName().equals("test")) {
//
////            for (int x = 0; x < 16; x++) {
////                for (int z = 0; z < 16; z++) {
//                    switch (chunkSnapshot.getBiome(8, 130, 8)) {
//                        case PLAINS -> BiomeHelper.setCustomBiome("serene_oasis:plains", chunk);
//                        case FOREST -> BiomeHelper.setCustomBiome("serene_oasis:forest", chunk);
//                        case RIVER -> BiomeHelper.setCustomBiome("serene_oasis:river", chunk);
//                        case JUNGLE -> BiomeHelper.setCustomBiome("serene_oasis:jungle", chunk);
//                        case OCEAN -> BiomeHelper.setCustomBiome("serene_oasis:ocean", chunk);
//                        case DESERT -> BiomeHelper.setCustomBiome("serene_oasis:desert", chunk);
//                    }
////                }
////            }
//        }
//
//
//
////        BiomeHelper.setCustomBiome("serene_oasis:" + key, event.getChunk());
//    }

//    @EventHandler
//    public void onChunkLoad(ChunkLoadEvent event){
//        String key = event.getChunk().getWorld().getBiome(new Location(event.getWorld(), event.getChunk().getX(), 0, event.getChunk().getZ()) ).getKey().getKey();
//        BiomeHelper.setCustomBiome("serene_oasis:" + key, event.getChunk());
//    }

    private static final HashMap<UUID, Biome> biomeTracker = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent){
        Player player = playerMoveEvent.getPlayer();
        World world = player.getWorld();
        Biome newBiome = world.getBiome(playerMoveEvent.getTo());
        Biome previousBiome = biomeTracker.get(player.getUniqueId());
        if (previousBiome != newBiome){
            player.sendTitle(newBiome.getKey().toString(), "Welcome!", 10, 40, 20 );
            biomeTracker.put(player.getUniqueId(), newBiome);
        }
    }

}
