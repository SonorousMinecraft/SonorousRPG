package com.sereneoasis.utils;

import com.sereneoasis.SereneRPG;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class VelocityUtils {

//    public static void applyVelocity(LivingEntity livingEntity, Vector velocity){
//        net.minecraft.world.entity.LivingEntity nmsLivingEntity = ((CraftLivingEntity)livingEntity).getHandle();
//        nmsLivingEntity.setJumping(true);
//        nmsLivingEntity.moveTo(nmsLivingEntity.position().add(new Vec3(velocity.getX(), velocity.getY(), velocity.getZ())));
//        Bukkit.broadcastMessage(String.valueOf(nmsLivingEntity.getDeltaMovement().length()));
////        livingEntity.setVelocity(velocity);
//    }

    public static void applyVelocityDamageEvent(LivingEntity livingEntity, Vector velocity){
        Bukkit.getScheduler().runTaskLater(SereneRPG.plugin, () -> {
            livingEntity.setVelocity(velocity);
        }, 1L);
    }
}
