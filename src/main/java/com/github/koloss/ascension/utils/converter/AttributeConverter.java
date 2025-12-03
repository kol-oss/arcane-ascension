package com.github.koloss.ascension.utils.converter;

import org.bukkit.attribute.Attribute;

import java.util.Map;

public final class AttributeConverter {
    private static final String DEFAULT_NAME = "Unknown";

    private static final Map<Attribute, String> NAME_BY_ATTRIBUTE = Map.of(
            Attribute.ATTACK_DAMAGE, "Damage",
            Attribute.ATTACK_KNOCKBACK, "Knockback",
            Attribute.ATTACK_SPEED, "Attack Speed",
            Attribute.KNOCKBACK_RESISTANCE, "Knockback Resistance",
            Attribute.ARMOR_TOUGHNESS, "Armor Toughness"
    );

    public static String toName(Attribute attribute) {
        return NAME_BY_ATTRIBUTE.getOrDefault(attribute, DEFAULT_NAME);
    }
}
