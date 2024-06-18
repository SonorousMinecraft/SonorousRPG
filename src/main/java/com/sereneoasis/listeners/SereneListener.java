package com.sereneoasis.listeners;

import com.github.javafaker.Faker;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.sereneoasis.SereneRPG;
import com.sereneoasis.npc.guis.MainGUI;
import com.sereneoasis.npc.guis.quests.QuestGUI;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.npc.types.assassin.AssassinEntity;
import com.sereneoasis.utils.ClientboundPlayerInfoUpdatePacketWrapper;
import com.sereneoasis.utils.EconUtils;
import com.sereneoasis.utils.NPCUtils;
import com.sereneoasis.utils.PacketUtils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class SereneListener implements Listener {

    private static final HashMap<UUID, Biome> biomeTracker = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent playerMoveEvent){
        Player player = playerMoveEvent.getPlayer();
        World world = player.getWorld();
        Biome newBiome = world.getBiome(playerMoveEvent.getTo());
        Biome previousBiome = biomeTracker.get(player.getUniqueId());
        if (previousBiome != newBiome){
            player.sendTitle(newBiome.getKey().toString(), "Welcome!", 10, 40, 20 );
            biomeTracker.put(player.getUniqueId(), newBiome);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
        SereneRPG.plugin.getNpcs().forEach((serverPlayer) -> {
            ClientboundPlayerInfoUpdatePacketWrapper playerInfoPacket = new ClientboundPlayerInfoUpdatePacketWrapper(
                    EnumSet.of(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, ClientboundPlayerInfoUpdatePacket.Action.UPDATE_LATENCY),
                    serverPlayer,
                    180,
                    true
            );
            PacketUtils.sendPacket(playerInfoPacket.getPacket(), player);
        });
        });
        EconUtils.payPlayer(event.getPlayer(), 1000);

    }

    @EventHandler
    public void onRightClickNPC(PlayerInteractEntityEvent event){


        if (!event.getPlayer().getOpenInventory().getType().equals(InventoryType.CHEST)) {
            if (SereneRPG.plugin.getNpcs().stream().anyMatch(humanEntity -> humanEntity.getBukkitEntity().getUniqueId() == event.getRightClicked().getUniqueId())) {
                SereneRPG.plugin.getNpcs()
                        .stream()
                        .filter(humanEntity -> humanEntity.getBukkitEntity().getUniqueId() == event.getRightClicked().getUniqueId())
                        .findAny().get().openGUI(event.getPlayer());
                event.setCancelled(true);

//            new MainGUI().openGUI(event.getPlayer());
            }
        }

    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player player){
            if (event.getEntity() instanceof LivingEntity livingEntity &&  livingEntity.getHealth() < event.getDamage()) {
                QuestGUI.decrementHuntKilLTracker(player, livingEntity);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent moveEvent){
        Player player = moveEvent.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, 100, 100, 100);

        Set<UUID> validUUIDs = nearbyEntities.stream()
                .filter(entity -> SereneRPG.plugin.getNpcs().stream().anyMatch(humanEntity -> humanEntity.getBukkitEntity().getUniqueId() == entity.getUniqueId()))
                .map(entity -> entity.getUniqueId())
                .collect(Collectors.toSet());

        Set<NPCMaster> nearbyNPCs = SereneRPG.plugin.getNpcs().stream()
                .filter(npcMaster -> validUUIDs.contains(npcMaster.getBukkitEntity().getUniqueId()))
                .collect(Collectors.toSet());

        if (nearbyNPCs.size() < 5) {
            Faker faker = new Faker();
            NPCMaster npc = NPCUtils.spawnNPC(location, player, faker.name().firstName(), faker.name().firstName());
            SereneRPG.plugin.addNPC(npc);
        }

    }
//
//    @EventHandler
//    public void onLeave(PlayerQuitEvent event){
//        SereneRPG.getPacketListener().removePlayer(event.getPlayer());
//    }

}
