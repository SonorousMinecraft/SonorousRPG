package com.sereneoasis.player.adeptness.passives.tools;

import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.passives.mining.InventoryMagnet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class TreeCapitate extends Passive {



    public TreeCapitate() {
        super("TreeCapitate", List.of("Fell trees in one blow whilst sneaking"), 10, (event -> {
            if (event instanceof BlockBreakEvent blockBreakEvent){
                Player player = blockBreakEvent.getPlayer();
                if (player.isSneaking()) {
                    ItemStack heldItem = player.getInventory().getItemInMainHand();

                    if (Tag.ITEMS_AXES.isTagged(heldItem.getType())) {
                        if (isTree(blockBreakEvent.getBlock())) {

                            getDirectionalBlocks(blockBreakEvent.getBlock(), DIRECTIONS, BlockFace.SELF);


                                treeBlocks.forEach(block -> {
                                    block.getDrops(heldItem).forEach(itemStack -> {
                                        block.getWorld().dropItem(block.getLocation(), itemStack);
                                    });
                                    block.setType(Material.AIR);

                                });
                                ItemMeta heldItemMeta = heldItem.getItemMeta();

                                if (heldItemMeta instanceof Damageable damageable) {
                                    Bukkit.broadcastMessage(String.valueOf(damageable.getDamage()));
                                    int newDamage =  treeBlocks.size();
                                    if (newDamage > 0) {
                                        damageable.setDamage(newDamage);
                                        heldItem.setItemMeta(damageable);
                                    }
//                                    } else {
//                                        player.getInventory().remove(heldItem);
//                                        player.sendMessage("Your axe sadly broke");
//                                    }
                                }

                        }
                        treeBlocks.clear();
                    }
                }
            }
        }));
    }


    public static boolean isTree(Block block) {
        for (int heightFromOriginal = 0; heightFromOriginal < 20; heightFromOriginal++) {
            Block currentTrunkBlock = block.getRelative(BlockFace.DOWN, heightFromOriginal);
            if (currentTrunkBlock.getType().equals(Material.GRASS_BLOCK) || currentTrunkBlock.getType().equals(Material.DIRT)) {
                break;
            }
            if (!Tag.LOGS.isTagged(currentTrunkBlock.getType())) {
                return false;
            }
        }

        return true;
    }

    private static final Set<Material> LEAVES = Set.of(Material.OAK_LEAVES, Material.BIRCH_LEAVES, Material.JUNGLE_LEAVES, Material.CHERRY_LEAVES, Material.ACACIA_LEAVES);
    private static final Set<Material> LOGS = Set.of(Material.OAK_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.CHERRY_LOG, Material.ACACIA_LOG);
    private static final Set<BlockFace> DIRECTIONS = Set.of(BlockFace.UP, BlockFace.NORTH, BlockFace.EAST,BlockFace.SOUTH,BlockFace.WEST);

    private static Set<Block>treeBlocks = new HashSet<>();

    public static void getDirectionalBlocks(Block currentBlock, Set<BlockFace> validDirections, BlockFace direction){
        Block newBlock = currentBlock.getLocation().add(direction.getDirection()).getBlock();
        if (treeBlocks.size() >= 100){
            return;
        }

            if (LOGS.stream().anyMatch(material -> material.equals(newBlock.getType())) || LEAVES.stream().anyMatch(material -> material.equals(newBlock.getType()))) {

                validDirections.stream().forEach(blockFace -> {
                    Set<BlockFace> newValidDirections = validDirections.stream().filter(bFace -> !bFace.equals(blockFace.getOppositeFace())).collect(Collectors.toSet());
                    getDirectionalBlocks(newBlock, newValidDirections, blockFace);
                });
                treeBlocks.add(newBlock);
            }

    }

}









