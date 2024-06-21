package com.sereneoasis.listeners;

import com.sereneoasis.level.world.tree.TreeGenerationUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class SereneListener implements Listener {

    private static final HashMap<UUID, Biome> biomeTracker = new HashMap<>();

    private static final Random random = new Random();

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        if (random.nextDouble() < 0.002){

            Chunk chunk = event.getChunk();

            int snapshotX = random.nextInt(16);
            int y = 256;
            int snapshotZ = random.nextInt(16);

            while(event.getChunk().getChunkSnapshot(false, false, false).getBlockType(snapshotX, y, snapshotZ).isAir() && y > -64) {
                y--;
                if (y == 0 ){
                    return;
                }
            }
            int x = snapshotX + chunk.getX() * 16;
            int z = snapshotZ + chunk.getZ() * 16;
            Location loc = new Location(event.getWorld(), x, y, z);
            loc.setYaw(90 * random.nextInt(0, 4));

            TreeGenerationUtils.genRandomTree(loc, random);

//            Schematics.pasteClipboard("fort1", loc);

//            Bukkit.getServer().getLogger().log(Level.INFO, loc.toString());


        }
    }


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
