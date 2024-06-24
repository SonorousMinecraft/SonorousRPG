package com.sereneoasis.player.adeptness.passives.unarmed;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.utils.VelocityUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Toss extends Passive {
    public Toss() {
        super("Toss", List.of("Throw an entity around"), 1, (event -> {

            if (event instanceof PlayerInteractEntityEvent entityDamageByEntityEvent) {
                Player player = entityDamageByEntityEvent.getPlayer();
                if (entityDamageByEntityEvent.getRightClicked() instanceof LivingEntity livingEntity) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (type.isAir()) {
                            if (player.isSneaking()){
                                Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                                    VelocityUtils.applyVelocityDamageEvent(livingEntity, player.getEyeLocation().getDirection().multiply(2));

                                }, 5L);
                            }
                        }
                }
            }
        }));
    }
}