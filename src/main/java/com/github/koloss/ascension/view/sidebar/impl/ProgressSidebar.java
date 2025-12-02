package com.github.koloss.ascension.view.sidebar.impl;

import com.github.koloss.ascension.common.AspectParams;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.view.sidebar.Sidebar;
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
    private FaithService faithService;

    private Player player;
    private DivineAspect aspect;

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public String getTitle() {
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

        NamedTextColor titleColour = AspectParams.toTextColor(aspect);
        Component titleComponent = Component.text(aspect.name())
                .color(titleColour)
                .decorate(TextDecoration.BOLD);

        return serializer.serialize(titleComponent);
    }

    @Override
    public List<String> getLines() {
        UUID userId = player.getUniqueId();
        Faith faith = faithService.findByUserIdAndAspect(userId, aspect);
        if (faith == null)
            return List.of();

        long currExp = faith.getCount().get();
        long nextLevelExp = LevelUtils.getProgressOfNextLevel(currExp);

        boolean hasNextLevel = faithService.hasOpenedNextLevel(userId, aspect);
        int currLevel = faith.getLevel().get();

        return List.of(
                "§7=============",
                "§6Experience: §b" + currExp,
                "§6Next level: §b" + nextLevelExp,
                "",
                "§6Level: §b" + currLevel + (hasNextLevel ? " §e(!)" : ""),
                "§7============="
        );
    }
}
