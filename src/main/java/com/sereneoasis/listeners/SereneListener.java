package com.sereneoasis.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.AdeptnessGUI;
import com.sereneoasis.player.adeptness.AdeptnessPassivesManager;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.specialisation.SpecialisationGUI;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Stack;

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
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            double amount = event.getDamage() + livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

            if (event.getDamager() instanceof Player player) {
                SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
                switch (event.getCause()) {
                    case ENTITY_ATTACK -> {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (type.isAir()) {
                            serenePlayer.incrementAdeptness(PlayerAdeptness.UNARMED, amount);
                            AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.UNARMED, serenePlayer, event);


                        } else if (Tag.ITEMS_SWORDS.isTagged(type)) {
                            serenePlayer.incrementAdeptness(PlayerAdeptness.SWORDS, amount);
                        } else if (Tag.ITEMS_TOOLS.isTagged(type)) {
                            serenePlayer.incrementAdeptness(PlayerAdeptness.TOOLS, amount);
                        }
                    }
                }
            } else if (event.getDamager() instanceof Arrow arrow) {
                if (arrow.getShooter() instanceof Player player) {
                    SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
                    serenePlayer.incrementAdeptness(PlayerAdeptness.RANGED, amount);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (event.getPlayer() != null){
            SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
            double amount = ( 1+ event.getExpToDrop()) *  ( 1 + event.getBlock().getBreakSpeed(event.getPlayer()));
            if (Tag.ITEMS_PICKAXES.isTagged(event.getPlayer().getInventory().getItemInMainHand().getType()) && Tag.MINEABLE_PICKAXE.isTagged(event.getBlock().getType())) {
                serenePlayer.incrementAdeptness(PlayerAdeptness.MINING, amount);
                AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.MINING, serenePlayer, event);

            } else if (Tag.ITEMS_TOOLS.isTagged(event.getPlayer().getInventory().getItemInMainHand().getType())) {
                serenePlayer.incrementAdeptness(PlayerAdeptness.TOOLS, amount);
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
        serenePlayer.incrementAdeptness(PlayerAdeptness.MOVEMENT, 0.1);
        AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.MOVEMENT, serenePlayer, event);

    }

    @EventHandler
    public void onJump(PlayerJumpEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
        double amount = event.getFrom().distance(event.getTo()) * 0.1;
        serenePlayer.incrementAdeptness(PlayerAdeptness.MOVEMENT, amount);
        AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.MOVEMENT, serenePlayer, event);
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());
        AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.MOVEMENT, serenePlayer, event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof  Player player) {
            SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(player);
            AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.MOVEMENT, serenePlayer, event);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getPlayer());

        AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.UNARMED, serenePlayer, event);

    }


    @EventHandler
    public void onArrowFly(ArrowFlyEvent event){
        SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(event.getShooter());
        AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.RANGED, serenePlayer, event);
    }

    private static final HashMap<Arrow, Player> arrowShooters = new HashMap<>();

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event){
        if (event.getEntity() instanceof  Player player) {
            if (event.getProjectile() instanceof Arrow arrow) {
                arrowShooters.put(arrow, player);

                iterateThroughActiveArrows(arrow);
            }
        }
    }

    private void iterateThroughActiveArrows(Arrow arrow){
        Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
            if (arrowShooters.containsKey(arrow)) {
                Bukkit.broadcastMessage("test");
                SerenePlayer serenePlayer = SerenePlayer.getSerenePlayer(arrowShooters.get(arrow));
                ArrowFlyEvent arrowFlyEvent = new ArrowFlyEvent(arrowShooters.get(arrow), arrow);
                Bukkit.getPluginManager().callEvent(arrowFlyEvent);
                AdeptnessPassivesManager.checkForPassives(PlayerAdeptness.RANGED, serenePlayer, arrowFlyEvent);
                iterateThroughActiveArrows(arrow);
            }
        }, 1L);
    }

    @EventHandler
    public void onEntityHit(ProjectileHitEvent event){
        if (event.getEntity() instanceof Arrow arrow){
            arrowShooters.remove(arrow);
        }
    }



    @EventHandler
    public void onCraftingGuideClick(InventoryClickEvent event){
        if (event.getClickedInventory().getType() != null) {
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                Player player = (Player) event.getWhoClicked();
                if (event.isLeftClick()) {
                    if (event.getSlot() == 40) {
                        AdeptnessGUI adeptnessGUI = new AdeptnessGUI(player);
                        adeptnessGUI.openGUI(player);

                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType().equals(InventoryType.PLAYER)) {

        }
    }


}
