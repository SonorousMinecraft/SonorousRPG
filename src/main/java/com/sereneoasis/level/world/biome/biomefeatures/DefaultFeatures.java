package com.sereneoasis.level.world.biome.biomefeatures;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class DefaultFeatures {
    
    public static Feature ROCK;
    
    public DefaultFeatures(){

        HashMap< Vector, Material> rockMap = new HashMap<>();
        for (int x = -3; x < 3; x++) {
            for (int z = -3; z < 3; z++) {
                for (int y = 0; y < 6; y++) {
                    rockMap.put(new Vector(x, y, z), Material.STONE);
                }
            }
        }
        ROCK = new Feature(rockMap);
    }
}
