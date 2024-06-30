package com.sereneoasis.player.adeptness.passives.unarmed;

import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.utils.VelocityUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;


public class StoppingPunch extends Passive {
    public StoppingPunch() {
        super("StoppingPunch", List.of("Punches when standing still have more knockback"), 3, (event -> {

            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                    if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (type.isAir()) {
                            if (!player.isSneaking() && player.getVelocity().length() <= 0.1){
                                VelocityUtils.applyVelocityDamageEvent(livingEntity, player.getEyeLocation().getDirection().multiply(2));
                            }
                        }
                    }
                }
            }
        }));
    }
}