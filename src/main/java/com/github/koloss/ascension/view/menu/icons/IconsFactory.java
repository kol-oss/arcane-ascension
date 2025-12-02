package com.github.koloss.ascension.view.menu.icons;

import com.github.koloss.ascension.common.AspectParams;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.utils.NumberUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IconsFactory {
    public static ItemStack createSpellsListIcon() {
        ItemStack spellsItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta spellsItemMeta = spellsItem.getItemMeta();
        spellsItemMeta.displayName(Component.text("§eSpells"));
        spellsItem.setItemMeta(spellsItemMeta);

        return spellsItem;
    }

    public static ItemStack createVillagesListIcon() {
        ItemStack villagesStack = new ItemStack(Material.BELL);
        ItemMeta villagesItemMeta = villagesStack.getItemMeta();
        villagesItemMeta.displayName(Component.text("§bVillages"));
        villagesStack.setItemMeta(villagesItemMeta);

        return villagesStack;
    }

    public static ItemStack createFollowIcon(boolean isFollowing) {
        String title = !isFollowing ? "Follow" : "Stop following";
        NamedTextColor titleColor = !isFollowing ? NamedTextColor.GREEN : NamedTextColor.RED;

        Component displayName = Component
                .text(title, titleColor)
                .decoration(TextDecoration.ITALIC, false);

        Material iconMaterial = !isFollowing ? Material.COMPASS : Material.ENDER_EYE;

        ItemStack followStack = new ItemStack(iconMaterial);
        ItemMeta followMeta = followStack.getItemMeta();
        followMeta.displayName(displayName);

        String loreData = !isFollowing ?
                "Enable this aspect live info in sidebar" :
                "Disable this aspect live info in sidebar";

        List<Component> lore = List.of(
                Component
                        .text(loreData, NamedTextColor.GRAY)
                        .decorate(TextDecoration.ITALIC)
        );
        followMeta.lore(lore);

        followStack.setItemMeta(followMeta);
        followStack.addItemFlags(ItemFlag.values());

        return followStack;
    }

    public static ItemStack createReturnIcon() {
        Component displayName = Component
                .text("Return", NamedTextColor.RED)
                .decoration(TextDecoration.ITALIC, false);

        ItemStack returnStack = new ItemStack(Material.BARRIER);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(displayName);
        returnStack.setItemMeta(returnMeta);
        returnStack.addItemFlags(ItemFlag.values());

        return returnStack;
    }

    public static ItemStack createAspectIcon(DivineAspect aspect, Long currExp) {
        String title = AspectParams.toString(aspect);
        NamedTextColor color = AspectParams.toTextColor(aspect);

        Component displayName = Component
                .text(title, color)
                .decoration(TextDecoration.ITALIC, false);

        Material material = AspectParams.toMaterial(aspect);

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);

        if (currExp != null) {
            List<Component> lore = List.of(
                    Component.text(""),
                    Component.text("§6Experience: §b" + currExp),
                    Component
                            .text(AspectParams.toDescription(aspect), NamedTextColor.GRAY)
                            .decorate(TextDecoration.ITALIC)
            );

            itemMeta.lore(lore);
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.addItemFlags(ItemFlag.values());

        return itemStack;
    }

    public static ItemStack createLevelIcon(DivineAspect aspect, Material material, int level) {
        String levelName = AspectParams.toString(aspect) + " " + NumberUtils.toRoman(level);
        NamedTextColor color = AspectParams.toTextColor(aspect);

        Component displayName = Component
                .text(levelName, color)
                .decoration(TextDecoration.ITALIC, false);

        ItemStack levelStack = new ItemStack(material);
        ItemMeta itemMeta = levelStack.getItemMeta();
        itemMeta.displayName(displayName);
        levelStack.setItemMeta(itemMeta);
        levelStack.addItemFlags(ItemFlag.values());

        return levelStack;
    }
}
