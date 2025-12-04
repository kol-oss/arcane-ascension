package com.github.koloss.ascension.controller.command;

import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.MessageUtils;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@AllArgsConstructor
public class SkillCommand implements CommandExecutor {
    private SkillService skillService;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Component.text("Wrong arguments number, required at least 1", NamedTextColor.RED));
            return true;
        }

        String typeString = args[0].toUpperCase();
        SkillType type;

        try {
            type = SkillType.valueOf(typeString);
        } catch (Exception ex) {
            player.sendMessage(Component.text("Invalid skill type, allowed only combat, mining or farming", NamedTextColor.RED));
            return true;
        }

        UUID userId = player.getUniqueId();

        Skill skill = skillService.findByUserIdAndType(userId, type);
        Component component = MessageUtils.getSkillContent(skill);

        player.sendMessage(component);
        return true;
    }
}
