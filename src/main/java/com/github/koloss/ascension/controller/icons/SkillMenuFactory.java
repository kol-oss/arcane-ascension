package com.github.koloss.ascension.controller.icons;

import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.utils.NumberUtils;
import com.github.koloss.ascension.utils.SkillTypeUtils;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkillMenuFactory {
    private static final String NESTED_SYMBOL = "  ";

    public static ItemStack createFollowIcon(boolean isDisplayed) {
        String title = isDisplayed ? "Stop following" : "Follow";
        NamedTextColor titleColor = isDisplayed ? NamedTextColor.RED : NamedTextColor.GREEN;

        Material titleMaterial = isDisplayed ? Material.ENDER_EYE : Material.COMPASS;

        return IconBuilder
                .of(title, titleColor, titleMaterial)
                .lore((isDisplayed ? "Hide" : "Display") + " this skill in sidebar", NamedTextColor.GRAY)
                .build();
    }

    public static ItemStack createSkillTypeIcon(SkillType type, Long progress) {
        String title = SkillTypeUtils.toString(type);
        NamedTextColor titleColor = SkillTypeUtils.toTextColor(type);
        Material titleMaterial = SkillTypeUtils.toMaterial(type);

        IconBuilder builder = IconBuilder.of(title, titleColor, titleMaterial);
        if (progress == null) {
            return builder.build();
        }

        return builder
                .loreEmpty()
                .lore("Progress: ", NamedTextColor.GOLD)
                .loreAppend(progress.toString(), NamedTextColor.AQUA)
                .lore(SkillTypeUtils.toDescription(type), NamedTextColor.GRAY)
                .build();
    }

    public static ItemStack createLevelIcon(SkillType type, Material material, int level) {
        String levelString = NumberUtils.toRoman(level);

        String title = SkillTypeUtils.toString(type) + " Level " + levelString;
        NamedTextColor titleColor = SkillTypeUtils.toTextColor(type);

        String abilityName = SkillTypeUtils.toAbilityString(type);

        int oldBuff = LevelUtils.getBuffForLevel(level - 1);
        int buff = LevelUtils.getBuffForLevel(level);

        return IconBuilder
                .of(title, titleColor, material)
                .lore("Rewards: ", NamedTextColor.GRAY)
                .lore(NESTED_SYMBOL + abilityName + " " + levelString, NamedTextColor.GOLD)
                .lore(NESTED_SYMBOL.repeat(2) + "Deal ")
                .loreAppend(oldBuff + "➜", NamedTextColor.DARK_GRAY)
                .loreAppend(buff + "%", NamedTextColor.GREEN)
                .loreAppend(" more " + SkillTypeUtils.toBuffDescription(type))
                .loreEmpty()
                .lore(NESTED_SYMBOL + "+200 mana", NamedTextColor.AQUA)
                .build();
    }
}
