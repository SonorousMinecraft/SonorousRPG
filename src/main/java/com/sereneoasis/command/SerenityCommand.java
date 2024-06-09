package com.sereneoasis.command;

import com.sereneoasis.utils.SchematicUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChunkSnapshot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;

public class SerenityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            String schemName = strings[0];
            if (schemName != null){
                try {
                    SchematicUtils.spawnSchematic(schemName, player.getLocation());
                } catch (IOException e) {
                    Bukkit.getServer().getLogger().log(Level.INFO, "oof");
                }
            }
        }
        return false;
    }
}
