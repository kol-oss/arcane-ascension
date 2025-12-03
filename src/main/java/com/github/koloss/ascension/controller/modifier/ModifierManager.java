package com.github.koloss.ascension.controller.modifier;

import com.github.koloss.ascension.model.SkillType;
import org.bukkit.entity.Player;

public interface ModifierManager {
    void apply(Player player, SkillType skillType);
}
