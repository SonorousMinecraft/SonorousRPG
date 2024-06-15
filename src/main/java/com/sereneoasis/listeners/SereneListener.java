package com.sereneoasis.listeners;

import com.sereneoasis.SereneRPG;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class SereneListener implements Listener {

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

//    @EventHandler
//    public void onJoin(PlayerJoinEvent event){
//        SereneRPG.getPacketListener().injectPlayer(event.getPlayer());
//    }
//
//    @EventHandler
//    public void onLeave(PlayerQuitEvent event){
//        SereneRPG.getPacketListener().removePlayer(event.getPlayer());
//    }

}
