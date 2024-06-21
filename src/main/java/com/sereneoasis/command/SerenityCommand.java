package com.sereneoasis.command;

import com.sereneoasis.enchantments.EnchantmentWrapper;
import com.sereneoasis.enchantments.Enchantments;
import com.sereneoasis.items.ItemStacks;
import com.sereneoasis.level.world.tree.TreeGenerationUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class SerenityCommand implements CommandExecutor {

//    private static final Set<ChatMaster> chatMasters = new HashSet<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
//        if (commandSender instanceof Player player) {
        Player player = Bukkit.getPlayer("Sakrajin");
            if (strings.length == 0) {
                
            }
            switch (strings[0]) {
                case "sword" -> {
                    ItemStack itemStack = ItemStacks.BASIC_SOLDIER_SWORD.getItemStack();
                    new EnchantmentWrapper(itemStack, Enchantments.FROSTBANE);
                    player.getInventory().addItem(itemStack);
                }
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
            }
        return true;
    }
}