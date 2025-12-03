package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.utils.converter.NumberConverter;
import org.bukkit.attribute.Attribute;

import java.util.Map;

public class MinerModifier extends BaseModifier {
    @Override
    public String getName() {
        return "Miner";
    }

    @Override
    protected int getMinLevel() {
        return 1;
    }

    // each level +2% luck (open after level 15)
    private double getEfficiencyScale(int level) {
        int minLevel = 1;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    // each level +3% range (open after level 15)
    private double getInteractionRangeScale(int level) {
        int minLevel = 15;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 3);
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
                Attribute.MINING_EFFICIENCY, getEfficiencyScale(level),
                Attribute.BLOCK_INTERACTION_RANGE, getInteractionRangeScale(level),
                Attribute.LUCK, getLuckScale(level)
        );
    }
}
