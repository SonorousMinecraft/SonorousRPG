package com.sereneoasis.player.adeptness.passives.movement;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.sereneoasis.player.adeptness.Passive;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DoubleJump extends Passive {

    private static final Set<Player> jumpPrepare = new HashSet<>();
    public DoubleJump() {
        super("Double Jump", List.of("Players can double jump"), 1, (event -> {
            if (event instanceof PlayerJumpEvent jumpEvent)
            {
                Player player = jumpEvent.getPlayer();
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    if (!jumpPrepare.contains(player)) {
                        player.setAllowFlight(true);
                        player.setFlying(false);
                        jumpPrepare.add(player);
                    }
                }
            } else if (event instanceof PlayerToggleFlightEvent flightEvent){
                Player player = flightEvent.getPlayer();
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {

                    if (jumpPrepare.contains(player)) {
                        jumpPrepare.remove(player);
                        player.setAllowFlight(false);

                        player.setFlying(false);
                        player.setVelocity(new Vector(0, 0.5, 0).add(player.getEyeLocation().getDirection()));

                        flightEvent.setCancelled(true);
                    }
                }
            } else if (event instanceof PlayerMoveEvent moveEvent){
                Player player = moveEvent.getPlayer();
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {

                    if (jumpPrepare.contains(player) && player.getLocation().subtract(0, 0.05, 0).getBlock().getType().isSolid() &&
                            player.getVelocity().getY() <= 0) {

                        jumpPrepare.remove(player);
                        player.setAllowFlight(false);
                    }
                }
            }
        }));
    }

}
