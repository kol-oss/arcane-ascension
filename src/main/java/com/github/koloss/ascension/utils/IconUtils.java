package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.constant.IconConstants;
import com.github.koloss.ascension.controller.icons.IconBuilder;
import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.converter.AttributeConverter;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;

import java.util.List;
import java.util.Map;

public final class IconUtils {
    public static void setModifierLore(IconBuilder builder, SkillType type, int level) {
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

            builder = builder.lore(IconConstants.NESTED_SYMBOL + modifierName + " " + modifierLevel, NamedTextColor.GOLD);
            for (Attribute attribute : scales.keySet()) {
                double prevScale = prevScales.get(attribute);
                double scale = scales.get(attribute);

                if (scale == 0)
                    continue;

                builder = builder
                        .lore(IconConstants.NESTED_SYMBOL.repeat(2) + "Grants ")
                        .loreAppend(NumberConverter.toPercent(prevScale) + "➜", NamedTextColor.DARK_GRAY)
                        .loreAppend(NumberConverter.toPercent(scale) + "%", NamedTextColor.GREEN)
                        .loreAppend(" " + AttributeConverter.toName(attribute), NamedTextColor.AQUA);
            }
        }
    }
}
