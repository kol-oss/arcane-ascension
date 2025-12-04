package com.github.koloss.ascension.controller.progress.impl;

import com.github.koloss.ascension.constant.ProgressConstants;
import com.github.koloss.ascension.controller.progress.ProgressHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class CombatProgressHandler implements ProgressHandler {
    private long processDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        EntityType type = entity.getType();

        return ProgressConstants.PROGRESS_BY_KILL.getOrDefault(type, 0L);
    }

    @Override
    public long getProgress(Event event) {
        if (event instanceof EntityDeathEvent) {
            return processDeath((EntityDeathEvent) event);
        }

        return 0L;
    }
}
