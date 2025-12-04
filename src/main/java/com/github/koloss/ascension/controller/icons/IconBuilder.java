package com.github.koloss.ascension.controller.icons;

import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class IconBuilder {
    private ItemStack itemStack;

    private static Component fromString(String string, NamedTextColor color) {
        return Component
                .text(string, color)
                .decoration(TextDecoration.ITALIC, false);
    }

    public static IconBuilder of(String displayName, NamedTextColor color, Material material) {
        return of(displayName, color, material, 1);
    }

    public static IconBuilder of(String displayName, NamedTextColor color, Material material, int count) {
        Component title = fromString(displayName, color);

        ItemStack iconStack = new ItemStack(material, count);
        ItemMeta iconMeta = iconStack.getItemMeta();
        iconMeta.displayName(title);
        iconStack.setItemMeta(iconMeta);
        iconStack.addItemFlags(ItemFlag.values());

        return new IconBuilder(iconStack);
    }

    public IconBuilder lore(Component component) {
        component = component.decoration(TextDecoration.ITALIC, false);

        List<Component> lore = itemStack.lore();
        if (lore == null)
            lore = new ArrayList<>();

        lore.add(component);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
