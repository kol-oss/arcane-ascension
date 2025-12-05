package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.controller.event.NewLevelAvailableEvent;
import com.github.koloss.ascension.controller.progress.manager.ProgressManager;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.LevelUtils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class ProgressListener implements Listener {
    private SkillService skillService;

    private void updateProgress(Event event, Player player) {
        UUID userId = player.getUniqueId();

        Map<SkillType, Long> progressByType = ProgressManager.getProgress(event);
        for (Map.Entry<SkillType, Long> progress : progressByType.entrySet()) {
            SkillType skillType = progress.getKey();
            Long value = progress.getValue();

            if (value == null || value == 0L) {
                continue;
            }

            Skill skill = skillService.findByUserIdAndType(userId, skillType);
            int beforeLevel = LevelUtils.getLevelFromProgress(skill.getProgress());

            skill.getProgressCount().addAndGet(value);
            int afterLevel = LevelUtils.getLevelFromProgress(skill.getProgress());

            skillService.update(skill);

            if (beforeLevel != afterLevel) {
                Bukkit.getPluginManager().callEvent(new NewLevelAvailableEvent(player, skillType, afterLevel));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }

        updateProgress(event, player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        updateProgress(event, player);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        updateProgress(event, player);
    }
}
