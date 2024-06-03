package com.sereneoasis;

import com.sereneoasis.level.world.biome.BiomeHelper;
import com.sereneoasis.level.world.biome.CustomBiomeProvider;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

public class SereneListener implements Listener {

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



}
