package com.sereneoasis.listeners;

import com.sereneoasis.enchantments.Enchantments;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentListener implements Listener {

    @EventHandler
    public void onAttackEvent(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player player){
            if (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR) ) {
                ItemStack heldItem = player.getInventory().getItemInMainHand();
                ItemMeta meta = heldItem.getItemMeta();
                if (meta.hasLore()) {
                    List<String> lore = meta.getLore()
                            .stream()
                            .map(ChatColor::stripColor)
                            .collect(Collectors.toList());
                    Entity target = event.getEntity();
                    switch (event.getCause()) {
                        case ENTITY_SWEEP_ATTACK -> {

                        }
                        case ENTITY_ATTACK -> {
                            Arrays.stream(Enchantments.values())
                                    .forEach(enchantments -> {
                                        if (lore.contains(enchantments.getName())){
                                            enchantments.getEnchantmentFunction().performFunction(player, target);
                                        }
                                    });
                        }
                    }
                }
            }
        }
    }
}
