package com.github.koloss.ascension.view.icons;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GeneralMenuFactory {
    public static ItemStack createSpellsListIcon() {
        String title = "Spells";
        Material material = Material.ENCHANTED_BOOK;

        return IconBuilder
                .of(title, NamedTextColor.YELLOW, material)
                .build();
    }

    public static ItemStack createVillagesListIcon() {
        String title = "Villages";
        Material material = Material.BELL;

        return IconBuilder
                .of(title, NamedTextColor.AQUA, material)
                .build();
    }
}
