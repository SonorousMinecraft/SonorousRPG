package com.sereneoasis.player.adeptness.passives.mining;

import com.sereneoasis.player.adeptness.AdeptnessPassivesManager;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class InventoryMagnet extends Passive {

    public InventoryMagnet() {
        super("Inventory Magnet", List.of("Causes broken blocks to go straight to your inventory"), 1, (event -> {
            if (event instanceof BlockBreakEvent blockBreakEvent){
                if (blockBreakEvent.isDropItems()){
                    Collection<ItemStack> drops = blockBreakEvent.getBlock().getDrops();
                    ((BlockBreakEvent) event).setDropItems(false);
                    Player player = blockBreakEvent.getPlayer();
                    drops.forEach(drop -> {
                        player.getInventory().addItem(drop);
                    });
                }
            }
        }));
    }

    public static void handleConflicts(Player player, ItemStack drop){
        if (AdeptnessPassivesManager.checkForPassive(PlayerAdeptness.MINING, player, InventoryMagnet.class)) {
            player.getInventory().addItem(drop);
        } else {
            player.getWorld().dropItem(player.getLocation(), drop);
        }
    }
}
