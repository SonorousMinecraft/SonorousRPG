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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerenityCommand implements CommandExecutor {

//    private static final Set<ChatMaster> chatMasters = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            if (strings.length == 0) {
                
            }
            switch (strings[0]) {
                case "tree" -> {
                    int iterations = Integer.valueOf(strings[2]);
                    switch (strings[1]) {
                        case "fir" -> {
                            TreeGenerationUtils.generateFirTree(player.getLocation(), iterations, new Random());
                        }
                        case "acacia" -> {
                            TreeGenerationUtils.generateAcaciaTree(player.getLocation(), iterations, new Random());
                        }
                        case "birch" -> {
                            TreeGenerationUtils.generateBirchTree(player.getLocation(), iterations, new Random());
                        }
                        case "spruce" -> {
                            TreeGenerationUtils.generateSpruceTree(player.getLocation(), iterations, new Random());
                        }
                        case "oak" -> {
                            TreeGenerationUtils.generateOakTree(player.getLocation(), iterations, new Random());
                        }
                        case "jungle" -> {
                            TreeGenerationUtils.generateJungleTree(player.getLocation(), iterations, new Random());
                        }
                        case "cherry" -> {
                            TreeGenerationUtils.generateCherryTree(player.getLocation(), iterations, new Random());
                        }
                        default -> {
                            player.sendMessage("Not a valid tree");
                        }

                    }
                }
                case "video" -> {
                    if (strings.length == 1){
                        return false;
                    }
                    if (strings[1].equals("browse")){
                        if (strings.length == 2){
                            return false;
                        }
                        try {
                            Document mainDoc = Jsoup.connect(strings[2]).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.79 Safari/537.36").get();

//                            Elements allLinks = new Elements();

                            List<String> allLinks = new ArrayList<>();
                            mainDoc.body().select("video").forEach(element -> {
                             allLinks.add(element.attr("data-mp4").toString());
                            });
                            mainDoc.body().select("video").forEach(element -> {
                                allLinks.add(element.attr("src").toString());
                            });

                            mainDoc.body().select("source").forEach(element -> {
                                allLinks.add(element.attr("src").toString());
                            });

                            mainDoc.head().select("meta").forEach(element -> {
                                if (element.attr("property").equals("og:image")) {
                                    String mediaLink = element.attr("content").toString();
                                    if (mediaLink.contains("gif")){
                                        allLinks.add(mediaLink);
                                    }


                                }
                            });

//                            for (String link : allLinks) {
                            String link = allLinks.get(new Random().nextInt(allLinks.size()));
                                if (link.contains("https://")) {
                                    Bukkit.broadcastMessage(link);

                                    new VideoFrameGrabber(link);
                                    break;
//                                }
                            }
                        } catch (IOException e) {
//                            throw new RuntimeException(e);
                        } catch (FrameGrabber.Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        try {
                            new VideoFrameGrabber(strings[1]);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (FrameGrabber.Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            
            
            
////            TreeGenerationUtils.generateCherryTree(player.getLocation(), iterations, new Random());
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

//
//            try {
//                new VideoFrameGrabber(strings[0]);
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (FrameGrabber.Exception e) {
//                throw new RuntimeException(e);
//            }

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