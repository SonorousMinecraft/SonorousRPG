package com.sereneoasis.player.adeptness.passives.swords;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.player.adeptness.Passive;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parry extends Passive {

    private final static Set<Player> PARRY_COOLDOWNS = new HashSet<>();
    public Parry() {
        super("Parry", List.of("Deflect a hit every 5 seconds"), 10, (event -> {
            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                    if (entityDamageByEntityEvent.getEntity() instanceof Player player) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (Tag.ITEMS_SWORDS.isTagged(type)) {
                            if (!PARRY_COOLDOWNS.contains(player)) {
                                player.sendMessage("Successful parry");
                                entityDamageByEntityEvent.setCancelled(true);
                                PARRY_COOLDOWNS.add(player);
                                Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
                                    PARRY_COOLDOWNS.remove(player);
                                }, 100L);

                            }
                        }
                    }
                }
        }));
    }
}