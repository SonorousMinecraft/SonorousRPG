package com.sereneoasis.npc.types.warrior;

import com.mojang.authlib.GameProfile;
import com.sereneoasis.entity.AI.goal.complex.combat.KillTargetEntity;
import com.sereneoasis.entity.AI.goal.complex.movement.RandomExploration;
import com.sereneoasis.entity.HumanEntity;
import com.sereneoasis.items.ItemStacks;
import com.sereneoasis.npc.types.GuiBuilder;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.utils.Vec3Utils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class WarriorEntity extends NPCMaster {


    public WarriorEntity(MinecraftServer server, ServerLevel world, GameProfile profile, ClientInformation clientOptions) {
        super(server, world, profile, clientOptions);
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
        return null;
    }

    @Override
    public HashMap<ItemStack, Pair<EntityType, Integer>> getHuntQuests() {
        HashMap<ItemStack, Pair<EntityType, Integer>> rewardHuntAmountMap = new HashMap<>();

        return null;
    }

    @Override
    public HashMap<ItemStack, Location> getExploreQuests() {
        HashMap<ItemStack, Location> rewardLocationMap = new HashMap<>();

        return null;
    }

    @Override
    public List<ItemStacks> getShopItems() {
        List<ItemStacks> shopArrayList = List.of();
        return shopArrayList;
    }
}
