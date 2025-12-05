package com.github.koloss.ascension.controller.items.impl;

import com.github.koloss.ascension.controller.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.controller.items.ItemHandler;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public class TomeOfAscension implements ItemHandler {
    private Plugin plugin;

    @Override
    public ItemStack create() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

        Component title = Component.text("Tome of Ascension")
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false);

        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.displayName(title);
        meta.setAuthor("The Trinitarian Scribes");
        book.setItemMeta(meta);

        return book;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Bukkit.getPluginManager().callEvent(new DisplayGeneralMenuEvent(player));
    }
}
