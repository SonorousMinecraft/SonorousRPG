package com.sereneoasis.player.adeptness.passives.ranged;

import com.sereneoasis.listeners.ArrowFlyEvent;
import com.sereneoasis.player.SerenePlayer;
import com.sereneoasis.player.adeptness.Passive;
import com.sereneoasis.player.adeptness.PassiveFunction;
import com.sereneoasis.player.adeptness.PlayerAdeptness;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;

public class ArrowRedirection extends Passive {
    public ArrowRedirection() {
        super("Arrow Redirection", List.of("Skilled archers can control their arrows"), 1, (event -> {
            if (event instanceof ArrowFlyEvent arrowFlyEvent) {
                Arrow arrow = arrowFlyEvent.getArrow();
                arrow.setVelocity(arrowFlyEvent.getShooter().getEyeLocation().getDirection().multiply(arrow.getVelocity().length()));
            }
        }));
    }
}
