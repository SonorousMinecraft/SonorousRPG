package com.sereneoasis.player.adeptness.passives.movement;

import com.sereneoasis.player.adeptness.Passive;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class WallClimb extends Passive {
    public WallClimb() {
        super("Wall Climb", List.of("Allows players to climb up walls"), 1, (event -> {
            if (event instanceof PlayerMoveEvent moveEvent)
            {
                Player player = moveEvent.getPlayer();
                if (isAgainstWall(player) && player.getLocation().add(player.getLocation().getDirection().multiply(0.5)).getBlock().getType().isSolid() ) {
                    player.setVelocity(new Vector(0,0.2,0));
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
