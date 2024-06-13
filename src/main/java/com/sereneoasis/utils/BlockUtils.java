package com.sereneoasis.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class BlockUtils {
    
    public static Set<Block> getBlocksAroundPoint(Location location, int diameter){
        Set<Block>blocks = new HashSet<>();
        for (double x = -diameter/2; x < diameter/2 ; x++){
            for (double y = -diameter/2; y < diameter/2 ; y++){
                for (double z = -diameter/2; z < diameter/2 ; z++){
                    blocks.add(location.clone().add(x,y,z).getBlock());
                }
            }
        }
        return blocks;
    }

    public static Set<Block> getCircleAroundPoint(Location location, int diameter){
        Set<Block>blocks = new HashSet<>();
        for (double x = -diameter/2; x < diameter/2 ; x++){
            for (double z = -diameter/2; z < diameter/2 ; z++){
                    blocks.add(location.clone().add(x,0,z).getBlock());
                }
        }
        return blocks;
    }

    public static Set<Block> getAirBlocksAroundPoint(Location location, int diameter){
        Set<Block>blocks = new HashSet<>();
        for (double x = -diameter/2; x < diameter/2 ; x++){
            for (double y = -diameter/2; y < diameter/2 ; y++){
                for (double z = -diameter/2; z < diameter/2 ; z++){
                    Block b = location.clone().add(x,y,z).getBlock();
                    if (b.getType().isAir()) {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }

    public static Set<Block> getAirCircleAroundPoint(Location location, int diameter){
        Set<Block>blocks = new HashSet<>();
        for (double x = (double) -diameter /2; x < (double) diameter /2 ; x++){
            for (double z = (double) -diameter /2; z < (double) diameter /2 ; z++){
                if ( (Math.abs(x) + Math.abs(z)) < (double) diameter /4) {
                    Block b = location.clone().add(x, 0, z).getBlock();
                    if (b.getType().isAir()) {
                        blocks.add(b);
                    }
                }
            }
        }
        return blocks;
    }
}
