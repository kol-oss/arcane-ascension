package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.controller.event.HelpEvent;
import com.github.koloss.ascension.items.ItemManager;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class ItemListener implements Listener {
    private ItemManager itemManager;

    @EventHandler
    public void onHelp(HelpEvent event) {
        Player player = event.getPlayer();
        ItemStack helpItem = itemManager.getHelpItem(player);

        if (helpItem != null) {
            Inventory inventory = player.getInventory();
            inventory.addItem(helpItem);
        } else if (event.isShowMessage()) {
            player.sendMessage(Component.text(
                    "Tome of Ascension is already present in inventory",
                    NamedTextColor.RED
            ));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            HelpEvent helpEvent = new HelpEvent(player, false);
            Bukkit.getPluginManager().callEvent(helpEvent);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null || !itemStack.hasItemMeta())
            return;

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        event.setCancelled(true);
        itemManager.process(event, itemStack);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        HelpEvent helpEvent = new HelpEvent(player, false);
        Bukkit.getPluginManager().callEvent(helpEvent);
    }
}
