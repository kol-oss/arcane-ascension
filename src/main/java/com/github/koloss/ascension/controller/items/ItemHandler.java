package com.github.koloss.ascension.controller.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface ItemHandler {
    ItemStack create();

    void onRightClick(PlayerInteractEvent event);
}
