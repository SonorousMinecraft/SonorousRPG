package com.sereneoasis.listeners;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.sereneoasis.SereneRPG;
import com.sereneoasis.utils.ClientboundPlayerInfoUpdatePacketWrapper;
import com.sereneoasis.utils.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

    }

    @EventHandler
    public void onRightClickNPC(PlayerInteractEntityEvent event){
        if (SereneRPG.plugin.getNpcs().stream().map(serverPlayer -> serverPlayer.getBukkitEntity()).anyMatch(craftPlayer -> craftPlayer == event.getRightClicked())) {
            //create a gui with 5 rows and the title My GUI
                    ChestGui gui = new ChestGui(5, "My GUI");
        //create a new pane occupying the entire gui
                    OutlinePane pane = new OutlinePane(0, 0, 9, 5);
                    ItemStack item = new ItemStack(Material.ICE);
        //create an item which will send a message when clicked
                    GuiItem guiItem = new GuiItem(item, e -> e.getWhoClicked().sendMessage("You clicked on ice!"));
        //add the item to the pane
                    pane.addItem(guiItem);
        //add the pane to the gui
                    gui.addPane(pane);

                    event.getPlayer().openInventory(gui.getInventory());
        }

    }
//
//    @EventHandler
//    public void onLeave(PlayerQuitEvent event){
//        SereneRPG.getPacketListener().removePlayer(event.getPlayer());
//    }

}
