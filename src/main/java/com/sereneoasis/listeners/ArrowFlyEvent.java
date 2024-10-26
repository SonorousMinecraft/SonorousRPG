package com.sereneoasis.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArrowFlyEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Player shooter;
    private final Arrow arrow;

    public ArrowFlyEvent(Player shooter, Arrow arrow){
        this.shooter = shooter;
        this.arrow = arrow;
    }

    public Player getShooter() {
        return shooter;
    }

    public Arrow getArrow() {
        return arrow;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public static HandlerList getHandlerList() {
        return new HandlerList();
    }
}
