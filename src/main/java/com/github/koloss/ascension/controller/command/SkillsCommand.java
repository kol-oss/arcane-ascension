package com.github.koloss.ascension.controller.command;

import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.MessageUtils;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public class SkillsCommand implements CommandExecutor {
    private SkillService skillService;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        UUID userId = player.getUniqueId();
        SkillType[] types = SkillType.values();

        for (SkillType type : types) {
            Skill skill = skillService.findByUserIdAndType(userId, type);
            Component component = MessageUtils.getSkillContent(type.name(), skill);

            player.sendMessage(component.appendNewline());
        }

        return true;
    }
}
