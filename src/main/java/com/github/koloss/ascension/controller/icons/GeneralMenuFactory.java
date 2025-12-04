package com.github.koloss.ascension.controller.icons;

import com.github.koloss.ascension.constant.IconConstants;
import com.github.koloss.ascension.constant.LevelConstants;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.IconUtils;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.utils.StringUtils;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GeneralMenuFactory {
    public static ItemStack createSpellsListIcon() {
        String title = "Spells";
        Material material = Material.ENCHANTED_BOOK;

        return IconBuilder
                .of(title, NamedTextColor.YELLOW, material)
                .build();
    }

    public static ItemStack createVillagesListIcon() {
        String title = "Villages";
        Material material = Material.BELL;

        return IconBuilder
                .of(title, NamedTextColor.AQUA, material)
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
                .lore(SkillTypeConverter.toDescription(type), NamedTextColor.GRAY);

        if (hasNextLevel && progress < (LevelConstants.MAX_LEVEL * LevelConstants.PROGRESS_PER_LEVEL - LevelConstants.PROGRESS_PER_LEVEL)) {
            int nextLevel = LevelUtils.getLevelFromProgress(progress) + 1;
            long nextProgress = LevelUtils.getProgressOfNextLevel(progress);

            double percent = 1 - (double) (nextProgress - progress) / LevelConstants.PROGRESS_PER_LEVEL;
            String progressString = StringUtils.getPart(IconConstants.PROGRESS_BAR, percent);

            builder.loreEmpty();
            builder.lore("Progress to Level " + NumberConverter.toRoman(nextLevel) + ": ", NamedTextColor.GRAY);
            builder.loreAppend((int) (percent * 100) + "%", NamedTextColor.GOLD);

            builder.lore(progressString, NamedTextColor.GREEN);
            builder.loreAppend(IconConstants.PROGRESS_BAR.substring(progressString.length()) + " ", NamedTextColor.WHITE);
            builder.loreAppend(progress % LevelConstants.PROGRESS_PER_LEVEL + "/" + LevelConstants.PROGRESS_PER_LEVEL, NamedTextColor.GOLD);

            builder.loreEmpty();
            builder.lore("Rewards of Level " + NumberConverter.toRoman(nextLevel) + ":", NamedTextColor.GRAY);
            IconUtils.setModifierLore(builder, type, nextLevel);
        }

        return builder.build();
    }
}
