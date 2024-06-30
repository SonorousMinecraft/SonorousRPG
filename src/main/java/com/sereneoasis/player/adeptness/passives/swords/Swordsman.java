package com.sereneoasis.player.adeptness.passives.swords;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.Passive;
import net.minecraft.world.InteractionHand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Swordsman extends Passive {
    public Swordsman() {
        super("Swordsman", List.of("Always sweep with full damage"), 20, (event -> {
            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                    if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (Tag.ITEMS_SWORDS.isTagged(type)) {
                            Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                                net.minecraft.world.entity.player.Player nmsPlayer = ((CraftPlayer) player).getHandle();
                                nmsPlayer.sweepAttack();
                                nmsPlayer.resetAttackStrengthTicker();

                            }, 1L);

                            //                            player.setCooldown(type, 0);
                        }
                    }
                }
            }
        }));
    }
}
