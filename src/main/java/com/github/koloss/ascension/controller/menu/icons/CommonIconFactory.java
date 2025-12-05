package com.github.koloss.ascension.controller.menu.icons;

import com.github.koloss.ascension.constant.LevelConstants;
import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.MessageUtils;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommonIconFactory {
    public static ItemStack createWaypointListIcon() {
        String title = "Waypoints";
        Material material = Material.BELL;

        return IconBuilder
                .of(title, NamedTextColor.AQUA, material)
                .build();
    }

    public static ItemStack createReturnIcon() {
        String title = "Return";
        Material material = Material.BARRIER;

        Component loreComponent = Component
                .text("Return to main menu", NamedTextColor.GRAY)
                .decorate(TextDecoration.ITALIC);

        return IconBuilder
                .of(title, NamedTextColor.RED, material)
                .lore(loreComponent)
                .build();
    }

    public static ItemStack createNextPageIcon() {
        String title = "Next page";
        Material material = Material.FILLED_MAP;

        return IconBuilder
                .of(title, NamedTextColor.DARK_GREEN, material)
                .build();
    }

    public static ItemStack createPrevPageIcon() {
        String title = "Previous page";
        Material material = Material.MAP;

        return IconBuilder
                .of(title, NamedTextColor.DARK_RED, material)
                .build();
    }

    public static ItemStack createSkillTypeIcon(Skill skill) {
        long progress = skill.getProgress();
        SkillType type = skill.getType();

        int level = skill.getLevel();
        boolean hasNextLevel = level < LevelConstants.MAX_LEVEL;

        String title = SkillTypeConverter.toString(type);
        NamedTextColor titleColor = SkillTypeConverter.toTextColor(type);
        Material titleMaterial = SkillTypeConverter.toMaterial(type);

        IconBuilder builder = IconBuilder
                .of(title, titleColor, titleMaterial)
                .lore(Component.text(SkillTypeConverter.toDescription(type), NamedTextColor.GRAY));

        if (hasNextLevel && progress < (LevelConstants.MAX_LEVEL * LevelConstants.PROGRESS_PER_LEVEL - LevelConstants.PROGRESS_PER_LEVEL)) {
            int nextLevel = skill.getLevel() + 1;

            builder
                    .lore(Component.empty())
                    .lore(MessageUtils.getProgressLevelString(nextLevel, progress))
                    .lore(MessageUtils.getProgressBar(progress, type))
                    .lore(Component.empty())
                    .lore(Component.text("Rewards of Level " + NumberConverter.toRoman(nextLevel) + ":", NamedTextColor.GRAY));

            List<SkillModifier> modifiers = ModifierFactory.getModifiers(type);
            for (SkillModifier modifier : modifiers) {
                Component modifierContent = MessageUtils.getModifierContent(modifier, nextLevel);
                if (modifierContent == null)
                    continue;

                Component component = MessageUtils.formatToLore(modifierContent);
                List<Component> rows = component.children();

                builder.lore(component.children(List.of()));
                for (Component child : rows)
                    builder.lore(child);
            }
        }

        return builder.build();
    }
}
