package com.github.koloss.ascension.controller.icons;

import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.converter.AttributeConverter;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

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
        String title = SkillTypeConverter.toString(type);
        NamedTextColor titleColor = SkillTypeConverter.toTextColor(type);
        Material titleMaterial = SkillTypeConverter.toMaterial(type);

        IconBuilder builder = IconBuilder.of(title, titleColor, titleMaterial);
        if (progress == null) {
            return builder.build();
        }

        return builder
                .loreEmpty()
                .lore("Progress: ", NamedTextColor.GOLD)
                .loreAppend(progress.toString(), NamedTextColor.AQUA)
                .lore(SkillTypeConverter.toDescription(type), NamedTextColor.GRAY)
                .build();
    }

    public static ItemStack createLevelIcon(SkillType type, Material material, int level) {
        String levelString = NumberConverter.toRoman(level);

        String title = SkillTypeConverter.toString(type) + " Level " + levelString;
        NamedTextColor titleColor = SkillTypeConverter.toTextColor(type);

        IconBuilder builder = IconBuilder
                .of(title, titleColor, material, level)
                .lore("Rewards: ", NamedTextColor.GRAY);

        List<SkillModifier> modifiers = ModifierFactory.getModifiers(type);
        for (SkillModifier modifier : modifiers) {
            String modifierName = modifier.getName();
            String modifierLevel = NumberConverter.toRoman(modifier.getLevel(level));

            Map<Attribute, Double> prevScales = modifier.getAttributes(level - 1);
            Map<Attribute, Double> scales = modifier.getAttributes(level);

            boolean hasScales = scales.values()
                    .stream()
                    .anyMatch(s -> s > 0);

            if (!hasScales)
                continue;

            builder = builder.lore(NESTED_SYMBOL + modifierName + " " + modifierLevel, NamedTextColor.GOLD);
            for (Attribute attribute : scales.keySet()) {
                double prevScale = prevScales.get(attribute);
                double scale = scales.get(attribute);

                if (scale == 0)
                    continue;

                builder = builder
                        .lore(NESTED_SYMBOL.repeat(2) + "Grants ")
                        .loreAppend(NumberConverter.toPercent(prevScale) + "➜", NamedTextColor.DARK_GRAY)
                        .loreAppend(NumberConverter.toPercent(scale) + "%", NamedTextColor.GREEN)
                        .loreAppend(" " + AttributeConverter.toName(attribute), NamedTextColor.AQUA);
            }
        }

        return builder.build();
    }
}
