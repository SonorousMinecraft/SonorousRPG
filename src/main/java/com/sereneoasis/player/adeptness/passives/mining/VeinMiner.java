package com.sereneoasis.player.adeptness.passives.mining;

import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class VeinMiner extends Passive {

    public VeinMiner() {
        super("Vein Miner", List.of("Mines adjacent ores"), 1, (event) -> {
            if (event instanceof BlockBreakEvent blockBreakEvent){
                if (blockBreakEvent.isDropItems()){
                    Block originalBlock  = blockBreakEvent.getBlock();
                    Player player = blockBreakEvent.getPlayer();

                    BlockUtils.getBlocksAroundPoint(originalBlock.getLocation().add(0.5,0.5,0.5), 3).forEach(veinBlock -> {
                        if (veinBlock.getType().equals(originalBlock.getType())){
                            veinBlock.getDrops(player.getInventory().getItemInMainHand()).forEach(itemStack -> {
                                InventoryMagnet.handleConflicts(player, itemStack);
                            });
                            veinBlock.setType(Material.AIR);
                        }
                    });
                }
            }
        });
    }
}
