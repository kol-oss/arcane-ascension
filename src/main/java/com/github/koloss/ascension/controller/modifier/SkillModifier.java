package com.github.koloss.ascension.controller.modifier;

import org.bukkit.attribute.Attribute;

import java.util.Map;

public interface SkillModifier {
    String getName();

    int getLevel(int level);

    Map<Attribute, Double> getAttributes(int level);
}
