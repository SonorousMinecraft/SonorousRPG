package com.sereneoasis.player.adeptness.passives.movement;

import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PassiveFunction;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class WallCling extends Passive {
    public WallCling() {
        super("Wall Cling", List.of("Allows players to cling to walls"), 1, (event -> {
            if (event instanceof PlayerMoveEvent moveEvent)
            {
                Player player = moveEvent.getPlayer();
                if (player.isSneaking()){
                    if (isAgainstWall(player)){
                        player.setVelocity(new Vector());
                    }
                }
            }
        }));
    }

    private static boolean isAgainstWall(Player player) {
        Location location = player.getLocation();
        if (location.getBlock().getRelative(BlockFace.NORTH).getType().isSolid()) {
            return true;
        } else if (location.getBlock().getRelative(BlockFace.SOUTH).getType().isSolid()) {
            return true;
        } else if (location.getBlock().getRelative(BlockFace.WEST).getType().isSolid()) {
            return true;
        } else return location.getBlock().getRelative(BlockFace.EAST).getType().isSolid();
    }

}
