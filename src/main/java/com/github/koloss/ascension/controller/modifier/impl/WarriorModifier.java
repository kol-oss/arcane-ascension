package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.utils.converter.NumberConverter;
import org.bukkit.attribute.Attribute;

import java.util.Map;

public class WarriorModifier extends BaseModifier {
    @Override
    public String getName() {
        return "Warrior";
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    // each level +2% damage
    private double getDamageScale(int level) {
        return NumberConverter.toScale(level * 2);
    }

    // each level +2% knockback (open after level 10)
    private double getKnockbackScale(int level) {
        int minLevel = 10;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 2);
    }

    // each level +3% attack speed (open after level 15)
    private double getAttackSpeedScale(int level) {
        int minLevel = 15;

        if (level < minLevel) return 0;
        return NumberConverter.toScale((level - minLevel + 1) * 3);
    }

    @Override
    public Map<Attribute, Double> getAttributesMap(int level) {
        return Map.of(
                Attribute.ATTACK_DAMAGE, getDamageScale(level),
                Attribute.ATTACK_KNOCKBACK, getKnockbackScale(level),
                Attribute.ATTACK_SPEED, getAttackSpeedScale(level)
        );
    }
}
