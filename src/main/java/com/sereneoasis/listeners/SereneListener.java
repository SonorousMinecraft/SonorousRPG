package com.sereneoasis.listeners;

import com.github.javafaker.Faker;
import com.sereneoasis.SereneRPG;
import com.sereneoasis.chat.ChatManager;
import com.sereneoasis.chat.ChatMaster;
import com.sereneoasis.chat.builders.ChatBuilder;
import com.sereneoasis.npc.guis.quests.QuestGUI;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.npc.types.NPCTypes;
import com.sereneoasis.utils.ClientboundPlayerInfoUpdatePacketWrapper;
import com.sereneoasis.utils.EconUtils;
import com.sereneoasis.utils.NPCUtils;
import com.sereneoasis.utils.PacketUtils;
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
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.IOException;
import java.net.URL;
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

        Player player = event.getPlayer();
        if (player.isSneaking()) {
            if (SereneRPG.plugin.getNpcs().stream().anyMatch(humanEntity -> humanEntity.getBukkitEntity().getUniqueId() == event.getRightClicked().getUniqueId())) {
                NPCTypes npcTypes = SereneRPG.plugin.getNpcs()
                        .stream()
                        .filter(humanEntity -> humanEntity.getBukkitEntity().getUniqueId() == event.getRightClicked().getUniqueId())
                        .findAny().get().getNPCType();


                ChatManager chatManager = new ChatManager();
                ChatBuilder chatBuilder = chatManager.getChatBuilder(npcTypes);

                ChatMaster chatMaster = new ChatMaster(player, chatBuilder);
            }
        }
        else {
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
    private void onItemFrameItemSpawn(ItemSpawnEvent event) {
        if(event.getEntity().getItemStack().getType() == Material.FILLED_MAP || event.getEntity().getItemStack().getType() == Material.ITEM_FRAME) {
            event.setCancelled(true);

        }

    }

   /* @EventHandler
    public void onMove(PlayerMoveEvent moveEvent) throws IOException {
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
//            if (Bukkit.getPlayer(faker.name().firstName()) != null) {

            String url = "https://api.mojang.com/users/profiles/minecraft/" + faker.name().firstName();
            try {
                Scanner scanner = new Scanner(new URL(url).openStream());

                NPCMaster npc = NPCUtils.spawnNPC(location, player, faker.name().firstName(), faker.name().firstName());
                SereneRPG.plugin.addNPC(npc);
            } catch (IOException ignored){

            }
        }

    }*/
//
//    @EventHandler
//    public void onLeave(PlayerQuitEvent event){
//        SereneRPG.getPacketListener().removePlayer(event.getPlayer());
//    }

}
