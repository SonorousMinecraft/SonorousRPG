package com.sereneoasis.npc.types.butcher;

import com.mojang.authlib.GameProfile;
import com.sereneoasis.entity.AI.goal.complex.combat.KillTargetEntity;
import com.sereneoasis.entity.AI.goal.complex.movement.RandomExploration;
import com.sereneoasis.entity.HumanEntity;
import com.sereneoasis.items.ItemCategory;
import com.sereneoasis.items.ItemStacks;
import com.sereneoasis.npc.types.GuiBuilder;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.npc.types.NPCTypes;
import com.sereneoasis.utils.Vec3Utils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ButcherEntity extends NPCMaster {


    public ButcherEntity(MinecraftServer server, ServerLevel world, GameProfile profile, ClientInformation clientOptions) {
        super(server, world, profile, clientOptions);

        this.setItemSlot(EquipmentSlot.FEET, net.minecraft.world.item.ItemStack.fromBukkitCopy(new org.bukkit.inventory.ItemStack(Material.LEATHER_BOOTS)));
    }

    @Override
    public NPCTypes getNPCType() {
        return NPCTypes.BUTCHER;
    }
    @Override
    public void tick() {
        super.tick();

        if (!masterGoalSelector.doingGoal("kill hostile entity")) {
            if (targetSelector.retrieveTopHostile() instanceof LivingEntity hostile && (!Vec3Utils.isObstructed(this.getPosition(0), hostile.getPosition(0), this.level()))) {
                masterGoalSelector.addMasterGoal(new KillTargetEntity("kill hostile entity", this, hostile));
            } else {
//                if (!masterGoalSelector.doingGoal("roam")) {
//                    masterGoalSelector.addMasterGoal(new RandomExploration("roam", this, null));
//                }
                if (!inventoryTracker.hasEnoughFood()) {
                    if (!masterGoalSelector.doingGoal("kill food entity")) {
                        if (targetSelector.retrieveTopPeaceful() instanceof LivingEntity peaceful) {
                            masterGoalSelector.addMasterGoal(new KillTargetEntity("kill food entity", this, peaceful));
                        }
                    }
                } else if (inventoryTracker.hasFood()) {
                    this.eat(this.level(), inventoryTracker.getMostAppropriateFood());
                }
            }
        }
    }


    @Override
    public HashMap<ItemStack, ItemStack> getAttainmentQuests() {
        HashMap<ItemStack, ItemStack> requirementRewardMap = new HashMap<>();
        requirementRewardMap.put(new ItemStack(Material.CHICKEN), ItemStacks.SLOW_ROASTED_CHICKEN.getItemStack());
        requirementRewardMap.put(new ItemStack(Material.BEEF), ItemStacks.SIRLOIN_STEAK.getItemStack());
        requirementRewardMap.put(new ItemStack(Material.PORKCHOP), ItemStacks.PAN_SEARED_PORK_CHOPS.getItemStack());
        requirementRewardMap.put(new ItemStack(Material.MUTTON), ItemStacks.LAMB_CHOPS.getItemStack());
        return requirementRewardMap;
    }

    @Override
    public HashMap<ItemStack, Pair<EntityType, Integer>> getHuntQuests() {
        HashMap<ItemStack, Pair<EntityType, Integer>> rewardHuntAmountMap = new HashMap<>();
        rewardHuntAmountMap.put(ItemStacks.SLOW_ROASTED_CHICKEN.getItemStack(), new Pair<>(EntityType.CHICKEN, 5));
        rewardHuntAmountMap.put(ItemStacks.SIRLOIN_STEAK.getItemStack(), new Pair<>(EntityType.COW, 5));
        rewardHuntAmountMap.put(ItemStacks.PAN_SEARED_PORK_CHOPS.getItemStack(), new Pair<>(EntityType.PIG, 5));
        rewardHuntAmountMap.put(ItemStacks.LAMB_CHOPS.getItemStack(), new Pair<>(EntityType.SHEEP, 5));
        return rewardHuntAmountMap;
    }

    @Override
    public HashMap<ItemStack, Location> getExploreQuests() {
        HashMap<ItemStack, Location> rewardLocationMap = new HashMap<>();

        return rewardLocationMap;
    }

    @Override
    public List<ItemStacks> getShopItems() {
        List<ItemStacks> shopArrayList = Arrays.stream(ItemStacks.values())
                .filter(itemStacks -> itemStacks.getCategory() == ItemCategory.MEAT)
                .collect(Collectors.toList());
        return shopArrayList;
    }
}
