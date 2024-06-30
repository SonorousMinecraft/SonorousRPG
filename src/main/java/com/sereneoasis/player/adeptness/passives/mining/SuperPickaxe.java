package com.sereneoasis.player.adeptness.passives.mining;

import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.adeptness.AdeptnessPassivesManager;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import com.sereneoasis.utils.BlockUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class SuperPickaxe extends Passive {

    public SuperPickaxe() {
        super("Super Pickaxe", List.of("Mine a 3x3 area"), 40, (event) -> {
            if (event instanceof BlockBreakEvent blockBreakEvent) {
                Block originalBlock = blockBreakEvent.getBlock();
                Player player = blockBreakEvent.getPlayer();

                BlockUtils.getBlocksAroundPoint(originalBlock, 3).forEach(veinBlock -> {
                    veinBlock.getDrops(player.getInventory().getItemInMainHand()).forEach(itemStack -> {
                        InventoryMagnet.handleConflicts(player, itemStack);
                    });
                    veinBlock.setType(Material.AIR);
                });
            }
        });
    }
}

