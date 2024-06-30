package com.sereneoasis.player.adeptness.passives.swords;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.Passive;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DoubleEdged extends Passive {
    public DoubleEdged() {
        super("Double Edged", List.of("Cut opponents twice"), 5, (event -> {
            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                    if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (Tag.ITEMS_SWORDS.isTagged(type)) {
                            double halfDamage = entityDamageByEntityEvent.getDamage() * 0.5;
                            entityDamageByEntityEvent.setDamage(halfDamage);
                            Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> livingEntity.damage(halfDamage), 10L);
                        }

                    }
                }
            }
        }));
    }
}