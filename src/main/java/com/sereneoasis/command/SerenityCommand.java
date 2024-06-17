package com.sereneoasis.command;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.entity.HumanEntity;
import com.sereneoasis.level.world.tree.TreeGenerationUtils;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.npc.types.assassin.AssassinEntity;
import com.sereneoasis.utils.NPCUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SerenityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
//            TreeGenerationUtils.generateCherryTree(player.getLocation(), 60, new Random());
            NPCMaster npc = NPCUtils.spawnNPC(player.getLocation(), player, strings[0], strings[0]);
//            SerenityEntities.getInstance().getNpcs().add(npc);
            SereneRPG.plugin.addNPC(npc);

            player.sendMessage("command run");
//            NPCUtils.updateEquipment(npc, player);

        }
        return false;
    }
}