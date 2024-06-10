package com.sereneoasis.level.world.biome.biomefeatures;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.function.Supplier;

/***
 * Contains features which will be created by default.
 * Currently unused - below is an example
 */
public class DefaultFeatures {

    // Creates a 6x6x6 rock of stone
    public static final Supplier<Feature> ROCK = () -> {
        HashMap< Vector, Material> rockMap = new HashMap<>();
        for (int x = -3; x < 3; x++) {
            for (int z = -3; z < 3; z++) {
                for (int y = 0; y < 6; y++) {
                    rockMap.put(new Vector(x, y, z), Material.STONE);
                }
            }
        }
        return new Feature(rockMap);
    } ;

}
