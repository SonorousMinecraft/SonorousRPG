package com.sereneoasis.command;

import com.sereneoasis.SereneRPG;
import com.sereneoasis.chat.ChatConfiguration;
import com.sereneoasis.chat.ChatManager;
import com.sereneoasis.chat.ChatMaster;
import com.sereneoasis.chat.builders.ChatBuilder;
import com.sereneoasis.entity.HumanEntity;
import com.sereneoasis.items.ItemStacks;
import com.sereneoasis.level.world.tree.TreeGenerationUtils;
import com.sereneoasis.npc.types.NPCMaster;
import com.sereneoasis.npc.types.assassin.AssassinEntity;
import com.sereneoasis.utils.NPCUtils;
import com.sereneoasis.video.MapRenderMan;
import com.sereneoasis.video.MapStitcher;
import com.sereneoasis.video.VideoFrameGrabber;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bytedeco.javacv.FrameGrabber;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SerenityCommand implements CommandExecutor {

//    private static final Set<ChatMaster> chatMasters = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
////            TreeGenerationUtils.generateCherryTree(player.getLocation(), 60, new Random());
//            NPCMaster npc = NPCUtils.spawnNPC(player.getLocation(), player, strings[0], strings[0]);
////            SerenityEntities.getInstance().getNpcs().add(npc);
//            SereneRPG.plugin.addNPC(npc);
//
//            player.sendMessage("command run");
////            NPCUtils.updateEquipment(npc, player);

//            if (strings.length ==  0) {
//                new MapStitcher(player);
//            } else {
//                new MapStitcher(player, strings[0]);
//            }


            try {
                new VideoFrameGrabber(strings[0]);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }

//            if (strings.length == 0){
//
//
//            } else {
//                switch (strings[0]) {
//                    case "next" -> {
//                        ChatMaster.getInstance(player).next(player, Integer.valueOf(strings[1]));
//                    }
//                    case "stop" -> {
//
//                    }
//                }
//            }
        }
        return true;
    }
}