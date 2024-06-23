package com.sereneoasis.listeners;

import com.sereneoasis.player.PlayerAdeptness;
import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.SpecialisationGUI;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class SereneListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        SerenePlayer.loadPlayer(event.getPlayer());
        new SpecialisationGUI().openGUI(event.getPlayer());

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        SerenePlayer.upsertPlayer(event.getPlayer());
    }


    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player player){
            SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
            switch (event.getCause()) {
                case ENTITY_ATTACK -> {
                    ItemStack heldItem = player.getInventory().getItemInMainHand();
                    Material type = (heldItem.getType());
                    if (type.isAir()) {
                        serenePlayer.incrementAdeptness(PlayerAdeptness.UNARMED);
                    }
                    else if (Tag.ITEMS_SWORDS.isTagged(type)){
                        serenePlayer.incrementAdeptness(PlayerAdeptness.SWORDS);
                    } else if (Tag.ITEMS_TOOLS.isTagged(type)) {
                        serenePlayer.incrementAdeptness(PlayerAdeptness.TOOLS);
                    }
                }
            }
        } else if (event.getDamager() instanceof Arrow arrow){
            if (arrow.getShooter() instanceof Player player){
                SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
                serenePlayer.incrementAdeptness(PlayerAdeptness.RANGED);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer() != null){
            SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
            if (Tag.ITEMS_PICKAXES.isTagged(event.getPlayer().getInventory().getItemInMainHand().getType()) && Tag.MINEABLE_PICKAXE.isTagged(event.getBlock().getType())) {
                serenePlayer.incrementAdeptness(PlayerAdeptness.MINING);

            } else if (Tag.ITEMS_TOOLS.isTagged(event.getPlayer().getInventory().getItemInMainHand().getType())) {
                serenePlayer.incrementAdeptness(PlayerAdeptness.TOOLS);
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
        serenePlayer.incrementAdeptness(PlayerAdeptness.MOVEMENT);

    }
}
