package com.sereneoasis.player.adeptness.passives.tools;


import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.passives.mining.InventoryMagnet;
import org.bukkit.*;
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

public class XPify extends Passive {
    public XPify() {
        super("XPify", List.of("Always obtain XP"), 1, (event -> {
            if (event instanceof BlockBreakEvent blockBreakEvent) {
                Player player = blockBreakEvent.getPlayer();
                if (!player.isSneaking()) {
                    ItemStack heldItem = player.getInventory().getItemInMainHand();

                    if (Tag.ITEMS_TOOLS.isTagged(heldItem.getType())) {
                        blockBreakEvent.setExpToDrop(blockBreakEvent.getExpToDrop() + 5);
                    }
                }
            }
        }));
    }
}
        