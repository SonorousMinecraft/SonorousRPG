package com.sereneoasis.player.adeptness.passives.swords;

import com.sereneoasis.listeners.ArrowFlyEvent;
import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.adeptness.AdeptnessPassivesManager;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class Decapitate extends Passive {
    public Decapitate() {
        super("Decapitate", List.of("Skilled archers can control their arrows"), 1, (event -> {
            if (event instanceof EntityDamageByEntityEvent entityDamageByEntityEvent) {
                if (entityDamageByEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
                    if (entityDamageByEntityEvent.getDamager() instanceof Player player) {
                        ItemStack heldItem = player.getInventory().getItemInMainHand();
                        Material type = (heldItem.getType());
                        if (Tag.ITEMS_SWORDS.isTagged(type)) {

                            switch (entityDamageByEntityEvent.getCause()) {
                                case ENTITY_ATTACK -> {
                                    if (livingEntity.getHealth() < entityDamageByEntityEvent.getDamage()){

                                        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                                        SkullMeta meta = (SkullMeta) item.getItemMeta();
                                        String name = livingEntity.getName().replace(" ", "_");
                                        Bukkit.broadcastMessage("MHF_" + name);
                                        meta.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_" +name));
                                        item.setItemMeta(meta);

                                        livingEntity.getLocation().getWorld().dropItem(livingEntity.getLocation(), item);

                                    }
                                }
                                case ENTITY_SWEEP_ATTACK -> {
//                                    if (livingEntity.getHealth() < entityDamageByEntityEvent.getDamage()){
//                                        Block block = livingEntity.getLocation().getBlock();
//                                        block.setType(Material.PLAYER_HEAD);
//
//                                        Bukkit.broadcastMessage("MHF_" + livingEntity.getName());
//                                        Skull skull = (Skull)block;
//                                        skull.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_" + livingEntity.getName()));
//
//
//                                    }
                                }
                            }
                        }
                    }
                }
            }
        }));
    }
}
