package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.utils.converter.NumberConverter;
import org.bukkit.attribute.Attribute;

import java.util.Map;

public class FarmhandModifier extends BaseModifier {
    @Override
    public String getName() {
        return "Farmhand";
    }

    @Override
    protected int getMinLevel() {
        return 1;
    }

    // each level +2% health
    private double getHealthScale(int level) {
        int minLevel = 1;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    // each level +2% luck (open after level 15)
    private double getSpeedScale(int level) {
        int minLevel = 15;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    // each level +3% luck (open after level 20)
    private double getLuckScale(int level) {
        int minLevel = 20;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 3);
    }

    @Override
    protected Map<Attribute, Double> getAttributesMap(int level) {
        return Map.of(
                Attribute.MAX_HEALTH, getHealthScale(level),
                Attribute.MOVEMENT_SPEED, getSpeedScale(level),
                Attribute.LUCK, getLuckScale(level)
        );
    }
}
