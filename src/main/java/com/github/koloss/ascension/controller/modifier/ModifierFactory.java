package com.github.koloss.ascension.controller.modifier;

import com.github.koloss.ascension.controller.modifier.impl.FarmhandModifier;
import com.github.koloss.ascension.controller.modifier.impl.GuardianModifier;
import com.github.koloss.ascension.controller.modifier.impl.MinerModifier;
import com.github.koloss.ascension.controller.modifier.impl.WarriorModifier;
import com.github.koloss.ascension.model.SkillType;

import java.util.List;
import java.util.Map;

public class ModifierFactory {
    private static final Map<SkillType, List<SkillModifier>> MODIFIERS_BY_TYPE = Map.of(
            SkillType.COMBAT, List.of(new WarriorModifier(), new GuardianModifier()),
            SkillType.MINING, List.of(new MinerModifier()),
            SkillType.FARMING, List.of(new FarmhandModifier())
    );

    public static List<SkillModifier> getModifiers(SkillType type) {
        return MODIFIERS_BY_TYPE.get(type);
    }
}
