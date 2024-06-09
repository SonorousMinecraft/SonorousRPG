package com.sereneoasis.level.world.biome.biomefeatures;

import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Feature {

    private HashMap<Vector, Material> vectorMaterialHashMap;

    public HashMap<Vector, Material> getVectorMaterialHashMap() {
        return vectorMaterialHashMap;
    }

    public Feature(HashMap<Vector, Material> vectorMaterialHashMap){
        this.vectorMaterialHashMap = vectorMaterialHashMap;
    }
}
