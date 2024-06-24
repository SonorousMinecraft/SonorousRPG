package com.sereneoasis.player.adeptness.passives.unarmed;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.utils.VelocityUtils;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.HumanoidArm;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OneTwo extends Passive {

    private static final Set<Player> RECURSIVE_PUNCH_PREVENT_SET = new HashSet<>();
    public OneTwo() {
        super("OneTwo", List.of("The good old reliable"), 1, (event -> {

            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                    if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                        if (!RECURSIVE_PUNCH_PREVENT_SET.contains(player)) {
                            ItemStack heldItem = player.getInventory().getItemInMainHand();
                            Material type = (heldItem.getType());
                            if (type.isAir()) {
                                if (!player.isSneaking() && player.getVelocity().length() > 0.1) {
                                    oneTwo(player, livingEntity);
                                    RECURSIVE_PUNCH_PREVENT_SET.add(player);
                                }
                            }
                        } else {
                            RECURSIVE_PUNCH_PREVENT_SET.remove(player);
                        }

                    }
                }
            }
        }));
    }

    public static void oneTwo(Player player, LivingEntity target) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        ServerPlayerConnection playerConnection = craftPlayer.getHandle().connection;
        ServerPlayer nmsPlayer = craftPlayer.getHandle();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        HumanoidArm mainHand = nmsPlayer.getMainArm();
        HumanoidArm offHand = mainHand.getOpposite();

//        ClientboundAnimatePacket clientboundAnimatePacket = new ClientboundAnimatePacket(craftPlayer.getHandle(), 0);
//        playerConnection.send(clientboundAnimatePacket);

        scheduler.runTaskLater(SereneRPG.plugin, () -> {
            nmsPlayer.setMainArm(offHand);
            ClientboundAnimatePacket clientboundAnimatePacket2 = new ClientboundAnimatePacket(craftPlayer.getHandle(), 0);
            playerConnection.send(clientboundAnimatePacket2);
            target.setNoDamageTicks(0);
            nmsPlayer.attack(((CraftLivingEntity)target).getHandle());

        }, 10L /*<-- the delay */);
        scheduler.runTaskLater(SereneRPG.plugin, () -> {
            nmsPlayer.setMainArm(mainHand);
        }, 20L /*<-- the delay */);

        RECURSIVE_PUNCH_PREVENT_SET.add(player);
    }
}

