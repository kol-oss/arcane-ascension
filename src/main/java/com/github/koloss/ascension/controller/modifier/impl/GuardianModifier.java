package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.utils.converter.NumberConverter;
import org.bukkit.attribute.Attribute;

import java.util.Map;

public class GuardianModifier extends BaseModifier {
    @Override
    public String getName() {
        return "Guardian";
    }

    @Override
    public int getMinLevel() {
        return 15;
    }

    // each level +2% knockback resistance (open after level 15)
    private double getKnockbackResistanceScale(int level) {
        int minLevel = 15;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    // each level +4% armor toughness (open after level 20)
    private double getArmorToughnessScale(int level) {
        int minLevel = 20;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    @Override
    public Map<Attribute, Double> getAttributesMap(int level) {
        return Map.of(
                Attribute.KNOCKBACK_RESISTANCE, getKnockbackResistanceScale(level),
                Attribute.ARMOR_TOUGHNESS, getArmorToughnessScale(level)
        );
    }
}
