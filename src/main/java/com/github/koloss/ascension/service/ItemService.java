package com.github.koloss.ascension.service;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public interface ItemService {
    void onJoin(PlayerJoinEvent event);

    void onInteract(PlayerInteractEvent event);

    void onRespawn(PlayerRespawnEvent event);
}
