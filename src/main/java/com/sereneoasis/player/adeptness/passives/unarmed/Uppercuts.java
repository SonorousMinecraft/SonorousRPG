package com.sereneoasis.player.adeptness.passives.unarmed;

import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PassiveFunction;
import com.sereneoasis.utils.VelocityUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Uppercuts extends Passive {
    public Uppercuts() {
        super("Uppercuts", List.of("Uppercut opponents by shift clicking"), 4, (event -> {

                if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                    if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                        if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                            ItemStack heldItem = player.getInventory().getItemInMainHand();
                            Material type = (heldItem.getType());
                            if (type.isAir()) {
                                if (player.isSneaking() && player.getLocation().subtract(0,0.05,0).getBlock().getType().isSolid()){
//                                    Bukkit.broadcastMessage(String.valueOf(livingEntity.getVelocity().getY()));
                                    VelocityUtils.applyVelocityDamageEvent(livingEntity, new Vector(0, 2.0, 0));
                                    player.setVelocity(new Vector(0, 2.0, 0));

                                }
                            }
                        }
                    }
                }
        }));
    }
}
