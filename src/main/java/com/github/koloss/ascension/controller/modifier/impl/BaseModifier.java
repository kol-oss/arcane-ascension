package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.controller.modifier.SkillModifier;
import org.bukkit.attribute.Attribute;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseModifier implements SkillModifier {
    protected abstract int getMinLevel();

    @Override
    public int getModifiedLevel(int level) {
        return level - getMinLevel() + 1;
    }

    protected abstract Map<Attribute, Double> getAttributesMap(int level);

    @Override
    public Map<Attribute, Double> getAttributes(int level) {
        return new LinkedHashMap<>(getAttributesMap(level));
    }
}
