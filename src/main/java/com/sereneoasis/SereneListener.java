package com.sereneoasis;

import com.sereneoasis.level.world.biome.BiomeHelper;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;

public class SereneListener implements Listener {

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event){
        String key = event.getChunk().getWorld().getBiome(new Location(event.getWorld(), event.getChunk().getX(), 0, event.getChunk().getZ()) ).getKey().getKey();
        BiomeHelper.setCustomBiome("serene_oasis:" + key, event.getChunk());
    }
}
