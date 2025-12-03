package com.github.koloss.ascension.view.icons;

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
        Component title = fromString(displayName, color);

        ItemStack iconStack = new ItemStack(material);
        ItemMeta iconMeta = iconStack.getItemMeta();
        iconMeta.displayName(title);
        iconStack.setItemMeta(iconMeta);
        iconStack.addItemFlags(ItemFlag.values());

        return new IconBuilder(iconStack);
    }

    public IconBuilder loreEmpty() {
        return lore(null);
    }

    public IconBuilder lore(String content) {
        return lore(content, NamedTextColor.WHITE);
    }

    public IconBuilder lore(String content, NamedTextColor color, TextDecoration... decorations) {
        Component newComponent;

        if (content != null && !content.isEmpty())
            newComponent = Component
                    .text(content, color)
                    .decoration(TextDecoration.ITALIC, false)
                    .decorate(decorations);
        else
            newComponent = Component.empty();

        List<Component> lore = itemStack.lore();
        if (lore == null)
            lore = new ArrayList<>();

        lore.add(newComponent);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public IconBuilder loreAppend(String content, TextDecoration... decorations) {
        return loreAppend(content, NamedTextColor.WHITE, decorations);
    }

    public IconBuilder loreAppend(String content, NamedTextColor color, TextDecoration... decorations) {
        if (content == null)
            return this;

        Component newComponent = Component
                .text(content, color)
                .decorate(decorations);

        List<Component> lore = itemStack.lore();
        if (lore == null) {
            lore = new ArrayList<>();
            lore.add(newComponent);
        } else {
            int lastIndex = lore.size() - 1;

            Component prevComponent = lore.get(lastIndex);
            lore.set(lastIndex, prevComponent.append(newComponent));
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
