package com.sereneoasis.command;

import com.sereneoasis.enchantments.EnchantmentWrapper;
import com.sereneoasis.enchantments.Enchantments;
import com.sereneoasis.items.ItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

            }
        return true;
    }
}