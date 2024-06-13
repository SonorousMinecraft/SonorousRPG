package com.sereneoasis.command;

import com.sereneoasis.level.world.tree.TreeGenerationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SerenityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            TreeGenerationUtils.generateBirchTree(player.getLocation(), 20, new Random());
        }
        return false;
    }
}