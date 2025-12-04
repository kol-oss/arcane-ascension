package com.github.koloss.ascension.controller.icons;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuFactory {
    public static ItemStack createReturnIcon() {
        String title = "Return";
        Material material = Material.BARRIER;

        Component loreComponent = Component
                .text("Return to main menu", NamedTextColor.GRAY)
                .decorate(TextDecoration.ITALIC);

        return IconBuilder
                .of(title, NamedTextColor.RED, material)
                .lore(loreComponent)
                .build();
    }
}
