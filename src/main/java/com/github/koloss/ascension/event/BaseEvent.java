package com.github.koloss.ascension.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    @Override
    public boolean isCancelled() {
        return cancelled.get();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled.set(cancelled);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
