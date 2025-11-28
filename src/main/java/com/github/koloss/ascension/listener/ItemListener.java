package com.github.koloss.ascension.listener;

import com.github.koloss.ascension.service.ItemService;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@AllArgsConstructor
public class ItemListener implements Listener {
    private ItemService itemService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        itemService.onJoin(event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        itemService.onInteract(event);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        itemService.onRespawn(event);
    }
}
