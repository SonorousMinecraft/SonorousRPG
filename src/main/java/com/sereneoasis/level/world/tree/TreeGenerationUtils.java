package com.sereneoasis.level.world.tree;

import com.sereneoasis.libs.FastNoiseLite;
import com.sereneoasis.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.util.Vector;
import oshi.util.tuples.Pair;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TreeGenerationUtils {

    public static void generateFirTree(Location origin, int iterations, Random random ){
        Location currentTrunkPos = origin.clone();

        Set<Location> trunk = new HashSet<>();
        Set<Pair<Vector, Location> >branches = new HashSet<>();

        for (int i = 0; i < iterations ; i ++){

            trunk.add(currentTrunkPos.clone().add(0,1,0));
            trunk.add(currentTrunkPos.clone().add(-1,0,0));
            trunk.add(currentTrunkPos.clone().add(1,0,0));
            trunk.add(currentTrunkPos.clone().add(0,0,-1));
            trunk.add(currentTrunkPos.clone().add(0,0,1));

            currentTrunkPos.add(random.nextDouble() - 0.5,1,random.nextDouble() - 0.5);

            Pair<Vector, Location> branch = new Pair<>(new Vector(random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5).normalize(), currentTrunkPos.clone());
            branches.add(branch);

        }

        branches.forEach(vectorLocationPair -> {
            for (int i = 0; i < random.nextInt(1, iterations) ; i ++) {
                vectorLocationPair.getB().add(vectorLocationPair.getA());
                vectorLocationPair.getB().getBlock().setType(Material.OAK_LOG);
                BlockUtils.getAirBlocksAroundPoint(vectorLocationPair.getB(), 3).forEach(block -> block.setType(Material.OAK_LEAVES));
            }});

        trunk.forEach(location -> location.getBlock().setType(Material.OAK_LOG));

    }

    public static void generateAcaciaTree(Location origin, int iterations, Random random ){
        Location currentTrunkPos = origin.clone();

        Set<Location> trunk = new HashSet<>();
        Set<Pair<Vector, Location> >branches = new HashSet<>();

        int currentHeight = 0;

        for (int i = 0; i < iterations ; i ++){

            trunk.add(currentTrunkPos.clone().add(0,1,0));
            trunk.add(currentTrunkPos.clone().add(-1,0,0));
            trunk.add(currentTrunkPos.clone().add(1,0,0));
            trunk.add(currentTrunkPos.clone().add(0,0,-1));
            trunk.add(currentTrunkPos.clone().add(0,0,1));

            currentTrunkPos.add(0,1, 0);
            currentHeight++;

            if (currentHeight > iterations/3 && currentHeight < iterations*2/3) {
                Pair<Vector, Location> branch = new Pair<>(new Vector(random.nextDouble() - 0.5, random.nextDouble() * currentHeight / 8, random.nextDouble() - 0.5).normalize(), currentTrunkPos.clone());
                branches.add(branch);
            }
        }

        branches.forEach(vectorLocationPair -> {
            for (int i = 0; i < iterations * 2/3 ; i ++) {
                vectorLocationPair.getB().add(vectorLocationPair.getA());
                vectorLocationPair.getB().getBlock().setType(Material.ACACIA_LOG);
                vectorLocationPair.getA().add(new Vector(0,0.2,0)).normalize();
            }
//            BlockUtils.getAirBlocksAroundPoint(vectorLocationPair.getB(), 10).forEach(block -> block.setType(Material.ACACIA_LEAVES));
        });

        FastNoiseLite noise = new FastNoiseLite(random.nextInt(100000));
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noise.SetFrequency(0.20F);
        noise.SetFractalType(FastNoiseLite.FractalType.PingPong);

        for (int height = -iterations/6; height < iterations/2; height++) {
            BlockUtils.getAirCircleAroundPoint(currentTrunkPos, (iterations*2 - height)  ).forEach(block -> {

                if (noise.GetNoise(block.getX(), block.getY(), block.getZ()) > 0.2) {
                    block.setType(Material.ACACIA_LEAVES);
                    Leaves blockData = (Leaves) block.getBlockData();
                    blockData.setPersistent(true);
                    block.setBlockData(blockData);

                }
            });
            currentTrunkPos.add(0,1,0);
        }


        trunk.forEach(location -> location.getBlock().setType(Material.ACACIA_LOG));

    }

    public static void generateBirchTree(Location origin, int iterations, Random random ){
        Location currentTrunkPos = origin.clone();

        Set<Location> trunk = new HashSet<>();
        Set<Pair<Vector, Location> >branches = new HashSet<>();

        for (int i = 0; i < iterations ; i ++){

            trunk.add(currentTrunkPos.clone().add(0,1,0));
            trunk.add(currentTrunkPos.clone().add(-1,0,0));
            trunk.add(currentTrunkPos.clone().add(1,0,0));
            trunk.add(currentTrunkPos.clone().add(0,0,-1));
            trunk.add(currentTrunkPos.clone().add(0,0,1));

            currentTrunkPos.add(random.nextDouble() - 0.5,1,random.nextDouble() - 0.5);

            Pair<Vector, Location> branch = new Pair<>(new Vector(random.nextDouble()-0.5, random.nextDouble()-0.5, random.nextDouble()-0.5).normalize(), currentTrunkPos.clone());
            branches.add(branch);

        }

        FastNoiseLite noise = new FastNoiseLite(random.nextInt(100000));
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noise.SetFrequency(0.20F);
        noise.SetFractalType(FastNoiseLite.FractalType.PingPong);

        branches.forEach(vectorLocationPair -> {
                    for (int i = 0; i < random.nextInt(1, iterations); i++) {
                        vectorLocationPair.getB().add(vectorLocationPair.getA());
                        vectorLocationPair.getB().getBlock().setType(Material.BIRCH_LOG);
                        vectorLocationPair.getA().add(new Vector(0,0.2,0)).normalize();

                    }
                    BlockUtils.getAirBlocksAroundPoint(vectorLocationPair.getB(), 7 ).forEach(block -> {
                        if (noise.GetNoise(block.getX(), block.getY(), block.getZ()) > 0.35) {
                            block.setType(Material.BIRCH_LEAVES);
                            Leaves blockData = (Leaves) block.getBlockData();
                            blockData.setPersistent(true);
                            block.setBlockData(blockData);

                        }
                    });

                }
            );

        trunk.forEach(location -> location.getBlock().setType(Material.BIRCH_LOG));

    }

}
