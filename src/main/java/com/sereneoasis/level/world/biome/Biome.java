package com.sereneoasis.level.world.biome;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.List;

/***
 * Represents data associated with a Vanilla Biome
 */
public abstract class Biome {

    protected org.bukkit.block.Biome biome;

    protected String name;

    protected HashMap<BiomeLayers, List<Material>>layers;

    protected double temperature, continentalness, humidity;

    public Biome(org.bukkit.block.Biome biome, String name, HashMap<BiomeLayers, List<Material>> layers, double temperature, double continentalness, double humidity){
        this.biome = biome;
        this.name = name;
        this.layers = layers;
        this.temperature = temperature;
        this.continentalness = continentalness;
        this.humidity = humidity;
    }


    /***
     * Represents the Vanilla Biome
     * @return The Vanilla Biome associated with this class
     */
    org.bukkit.block.Biome getBiome(){
        return this.biome;
    }

    /***
     * Returns the name
     * @return The name of the Biome which should be displayed
     */
    String getName() {
        return this.name;
    }

    /***
     * Represents the layers a Biome contains for World Generation purposes
     * @return A HashMap associating Biome layers with a list of materials
     */
    HashMap<BiomeLayers, List<Material>>getLayers(){
        return this.layers;
    }

    /***
     * Represents the temperature, used to dictate where it will generate
     * @return A double showing Biome temperature from -1 to 1
     */
    double getTemperature(){
        return this.temperature;
    }

    /***
     * Represents the continentalness, used to dictate how inland it will generate
     * @return A double showing continentalness from -1 to 1
     */
    double getContinentalness(){
        return this.temperature;
    }

    /***
     * Represents the humidity, used to dictate where it will generate
     * @return A double showing humidity from -1 to 1
     */
    double getHumidity(){
        return this.humidity;
    }



}
