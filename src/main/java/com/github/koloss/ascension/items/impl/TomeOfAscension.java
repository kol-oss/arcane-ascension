package com.github.koloss.ascension.items.impl;

import com.github.koloss.ascension.items.ItemHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class TomeOfAscension implements ItemHandler {
    @Override
    public ItemStack create() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);

        Component title = Component.text("Tome of Ascension")
                .color(NamedTextColor.BLUE)
                .decorate(TextDecoration.BOLD);

        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.displayName(title);
        meta.setAuthor("The Trinitarian Scribes");
        book.setItemMeta(meta);

        return book;
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        player.openInventory(player.getInventory());
    }
}
