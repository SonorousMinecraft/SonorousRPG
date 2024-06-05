package com.sereneoasis.level.world.biome;

import com.sereneoasis.level.world.biome.biomefeatures.FloraBiome;
import com.sereneoasis.level.world.biome.biomefeatures.TreeBiome;
import com.sereneoasis.utils.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

/***
 * Represents data associated with a Vanilla Biome
 */
public abstract class BiomeRepresentation {

    protected Biome biome;

    protected String name;

    protected HashMap<BiomeLayers, List<Material>>layers;

    protected double temperature, continentalness, humidity, weirdness = 0;

    private static final HashSet<BiomeRepresentation> BIOME_REPRESENTATIONS = new HashSet<>();

    public static HashSet<BiomeRepresentation> getBiomeRepresentations() {
        return BIOME_REPRESENTATIONS;
    }

    private static final HashMap<Biome, BiomeRepresentation> BIOME_MAP = new HashMap<>();

    public static HashMap<Biome, BiomeRepresentation> getBiomeMap() {
        return BIOME_MAP;
    }

    public static BiomeRepresentation getBiomeRepresentation(Biome biome){
        return BIOME_MAP.get(biome);
    }

    private static final HashSet<Biome> VALID_BIOMES = new HashSet<>();

    public static HashSet<Biome> getValidBiomes() {
        return VALID_BIOMES;
    }

    public static void initBiomes(){
        ReflectionUtils.findAllClasses("com.sereneoasis.level.world.biome.biomes").stream()
                .forEach(aClass -> {
                    try {
                        Bukkit.getServer().getLogger().log(Level.INFO, () -> aClass.getName() + " is loaded");
                        aClass.newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static final HashMap<Biome, TreeBiome>TREE_BIOMES = new HashMap<>();

    public static boolean isTreeBiome(Biome biome){
        return (TREE_BIOMES.containsKey(biome));
    }

    public static List<TreeType> getTreeTypes(Biome biome){
        return List.of(TREE_BIOMES.get(biome).getTreeType());
    }


    private static final HashMap<Biome, FloraBiome>FLORA_BIOMES = new HashMap<>();

    public static boolean isFloraBiome(Biome biome){
        return (FLORA_BIOMES.containsKey(biome));
    }

    public static HashMap<Integer, Material> getFloraTypes(Biome biome){
        return FLORA_BIOMES.get(biome).getFlora();
    }

    public BiomeRepresentation(org.bukkit.block.Biome biome, String name, HashMap<BiomeLayers, List<Material>> layers, double temperature, double continentalness, double humidity){
        this.biome = biome;
        this.name = name;
        this.layers = layers;
        this.temperature = temperature;
        this.continentalness = continentalness;
        this.humidity = humidity;
        BIOME_MAP.put(biome, this);
        BIOME_REPRESENTATIONS.add(this);
        VALID_BIOMES.add(biome);
        if (this instanceof TreeBiome treeBiome){
            TREE_BIOMES.put(biome, treeBiome);
        }
        if (this instanceof FloraBiome floraBiome){
            FLORA_BIOMES.put(biome, floraBiome);
        }
    }

    public BiomeRepresentation(org.bukkit.block.Biome biome, String name, HashMap<BiomeLayers, List<Material>> layers, double temperature, double continentalness, double humidity, double weirdness){
        this.biome = biome;
        this.name = name;
        this.layers = layers;
        this.temperature = temperature;
        this.continentalness = continentalness;
        this.humidity = humidity;
        this.weirdness = weirdness;
        BIOME_MAP.put(biome, this);
        BIOME_REPRESENTATIONS.add(this);
        VALID_BIOMES.add(biome);
        if (this instanceof TreeBiome treeBiome){
            TREE_BIOMES.put(biome, treeBiome);
        }
        if (this instanceof FloraBiome floraBiome){
            FLORA_BIOMES.put(biome, floraBiome);
        }
    }


    /***
     * Represents the Vanilla Biome
     * @return The Vanilla Biome associated with this class
     */
    public org.bukkit.block.Biome getBiome(){
        return this.biome;
    }

    /***
     * Returns the name
     * @return The name of the Biome which should be displayed
     */
    public String getName() {
        return this.name;
    }

    /***
     * Represents the layers a Biome contains for World Generation purposes
     * @return A HashMap associating Biome layers with a list of materials
     */
    public HashMap<BiomeLayers, List<Material>>getLayers(){
        return this.layers;
    }

    /***
     * Represents the temperature, used to dictate where it will generate
     * @return A double showing Biome temperature from -1 to 1
     */
    public double getTemperature(){
        return this.temperature;
    }

    /***
     * Represents the continentalness, used to dictate how inland it will generate
     * @return A double showing continentalness from -1 to 1
     */
    public double getContinentalness(){
        return this.temperature;
    }

    /***
     * Represents the humidity, used to dictate where it will generate
     * @return A double showing humidity from -1 to 1
     */
    public double getHumidity(){
        return this.humidity;
    }

    /***
     * Represents the weirdness, dictates whether a variant of a biome should spawn
     * @return A double which makes this biome less likely to spawn
     */
    public double getWeirdness() {
        return weirdness;
    }
}
