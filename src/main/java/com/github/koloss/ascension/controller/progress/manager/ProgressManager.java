package com.github.koloss.ascension.controller.progress.manager;

import com.github.koloss.ascension.controller.progress.ProgressHandler;
import com.github.koloss.ascension.controller.progress.impl.CombatProgressHandler;
import com.github.koloss.ascension.controller.progress.impl.FarmingProgressHandler;
import com.github.koloss.ascension.controller.progress.impl.MiningProgressHandler;
import com.github.koloss.ascension.model.SkillType;
import org.bukkit.event.Event;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProgressManager {
    private static final Map<SkillType, ProgressHandler> HANDLERS_BY_TYPE = Map.of(
            SkillType.COMBAT, new CombatProgressHandler(),
            SkillType.MINING, new MiningProgressHandler(),
            SkillType.FARMING, new FarmingProgressHandler()
    );

    public static Map<SkillType, Long> getProgress(Event event) {
        Set<Map.Entry<SkillType, ProgressHandler>> entries = HANDLERS_BY_TYPE.entrySet();
        return entries
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getProgress(event)
                ));
    }
}
