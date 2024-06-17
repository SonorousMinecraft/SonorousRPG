package com.sereneoasis.listeners;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.sereneoasis.SereneRPG;
import com.sereneoasis.npc.guis.MainGUI;
import com.sereneoasis.npc.guis.quests.QuestGUI;
import com.sereneoasis.utils.ClientboundPlayerInfoUpdatePacketWrapper;
import com.sereneoasis.utils.EconUtils;
import com.sereneoasis.utils.PacketUtils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.UUID;

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
        if (SereneRPG.plugin.getNpcs().stream().map(serverPlayer -> serverPlayer.getBukkitEntity()).anyMatch(craftPlayer -> craftPlayer == event.getRightClicked())) {
            new MainGUI(event.getPlayer()).openGUI(event.getPlayer());
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
//
//    @EventHandler
//    public void onLeave(PlayerQuitEvent event){
//        SereneRPG.getPacketListener().removePlayer(event.getPlayer());
//    }

}
