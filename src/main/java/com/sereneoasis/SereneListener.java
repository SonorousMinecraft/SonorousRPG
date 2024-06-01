package com.sereneoasis;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class SereneListener implements Listener {

    @EventHandler
    public void onChunkPreload(ChunkLoadEvent event){
        BiomeHelper.setCustomBiome("serene_rpg:gallifrey_badlands", event.getChunk());
    }
}
