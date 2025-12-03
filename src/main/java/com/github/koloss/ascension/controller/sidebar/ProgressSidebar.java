package com.github.koloss.ascension.controller.sidebar;

import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.LevelUtils;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ProgressSidebar implements Sidebar {
    private SkillService skillService;

    private Player player;
    private SkillType skillType;

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getTitle() {
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

        NamedTextColor titleColour = SkillTypeConverter.toTextColor(skillType);
        Component titleComponent = Component.text(skillType.name())
                .color(titleColour)
                .decorate(TextDecoration.BOLD);

        return serializer.serialize(titleComponent);
    }

    @Override
    public List<String> getLines() {
        UUID userId = player.getUniqueId();
        Skill skill = skillService.findByUserIdAndType(userId, skillType);

        long currProgress = skill.getProgress();
        long nextLevelProgress = LevelUtils.getProgressOfNextLevel(currProgress);

        boolean hasNextLevel = skillService.hasOpenedNextLevel(userId, skillType);
        int currLevel = skill.getLevel();

        return List.of(
                "§7=============",
                "§6Progress: §b" + currProgress,
                "§6Next level: §b" + nextLevelProgress,
                "",
                "§6Level: §b" + currLevel + (hasNextLevel ? " §e(!)" : ""),
                "§7============="
        );
    }
}
