package com.sereneoasis.player.adeptness.passives.mining;

import com.sereneoasis.player.adeptness.AdeptnessPassivesManager;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PassiveFunction;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class DoubleOres extends Passive {

    public DoubleOres() {
        super("Double Ores", List.of("Doubles the drops of ores"), 10, (event -> {
            if (event instanceof BlockBreakEvent blockBreakEvent){
                if (blockBreakEvent.isDropItems()){
                    Collection<ItemStack> drops = blockBreakEvent.getBlock().getDrops();
                    Player player = blockBreakEvent.getPlayer();
                    drops.forEach(drop -> {
                        InventoryMagnet.handleConflicts(player, drop);
                    });
                }
            }
        }));
    }
}
