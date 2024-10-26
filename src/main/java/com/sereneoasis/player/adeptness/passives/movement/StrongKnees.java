package com.sereneoasis.player.adeptness.passives.movement;

import com.sereneoasis.player.adeptness.Passive;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class StrongKnees extends Passive {
    public StrongKnees() {
        super("Strong Knees", List.of("Reduces fall damage"), 1, (event -> {
            if (event instanceof EntityDamageEvent moveEvent)
            {
                if (moveEvent.getEntity() instanceof Player player) {
                    if (moveEvent.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                        player.damage(Math.sqrt(moveEvent.getDamage()));
                        ((EntityDamageEvent) event).setCancelled(true);
                    }
                }
            }
        }));
    }

}
