package com.github.koloss.ascension.controller.icons;

import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.IconUtils;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkillMenuFactory {
    public static ItemStack createFollowIcon(boolean isDisplayed) {
        String title = isDisplayed ? "Stop following" : "Follow";
        NamedTextColor titleColor = isDisplayed ? NamedTextColor.RED : NamedTextColor.GREEN;

        Material titleMaterial = isDisplayed ? Material.ENDER_EYE : Material.COMPASS;

        return IconBuilder
                .of(title, titleColor, titleMaterial)
                .lore((isDisplayed ? "Hide" : "Display") + " this skill in sidebar", NamedTextColor.GRAY)
                .build();
    }

    public static ItemStack createLevelIcon(SkillType type, Material material, int level) {
        String levelString = NumberConverter.toRoman(level);

        String title = SkillTypeConverter.toString(type) + " Level " + levelString;
        NamedTextColor titleColor = SkillTypeConverter.toTextColor(type);

        IconBuilder builder = IconBuilder
                .of(title, titleColor, material, level)
                .lore("Rewards: ", NamedTextColor.GRAY);

        IconUtils.setModifierLore(builder, type, level);
        return builder.build();
    }
}
