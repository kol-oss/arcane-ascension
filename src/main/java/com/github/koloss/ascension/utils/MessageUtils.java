package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.constant.IconConstants;
import com.github.koloss.ascension.constant.LevelConstants;
import com.github.koloss.ascension.controller.event.DisplayProgressMenuEvent;
import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.converter.AttributeConverter;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class MessageUtils {
    private static final String PREFIX = " ";
    private static final Set<String> NEW_LINE_CHARACTERS = Set.of("\r", "\n", "\r\n");

    private static final String DELIMITER_SYMBOL = "=";
    private static final NamedTextColor DELIMITER_COLOR = NamedTextColor.YELLOW;

    private static boolean isNewLineComponent(Component source) {
        if (!(source instanceof TextComponent text))
            return false;

        return NEW_LINE_CHARACTERS.contains(text.content());
    }

    private static Component replaceNewLineWithEmpty(Component source) {
        if (!(source instanceof TextComponent))
            return source;

        return isNewLineComponent(source) ? Component.empty() : source;
    }

    public static Component formatToLore(Component source) {
        if (!(source instanceof TextComponent text))
            return source;

        List<Component> children = new ArrayList<>(source.children());
        children.replaceAll(MessageUtils::replaceNewLineWithEmpty);
        children.replaceAll(MessageUtils::formatToLore);

        return text.children(children);
    }

    public static Component getDelimiterLine(String content, NamedTextColor contentColor) {
        int width = 24;
        if (content == null || content.isEmpty())
            return Component.text(DELIMITER_SYMBOL.repeat(width), DELIMITER_COLOR);

        int formatedWidth = width - content.length() - 2;
        Component closeDelimiterPart = Component.text(DELIMITER_SYMBOL.repeat(formatedWidth / 2), DELIMITER_COLOR);

        return Component.text(DELIMITER_SYMBOL.repeat(formatedWidth / 2), DELIMITER_COLOR)
                .appendSpace()
                .append(Component.text(content, contentColor))
                .appendSpace()
                .append(closeDelimiterPart);
    }

    public static Component getProgressLevelString(int nextLevel, long progress) {
        long nextProgress = LevelUtils.getProgressOfNextLevel(progress);
        double percent = 1 - (double) (nextProgress - progress) / LevelConstants.PROGRESS_PER_LEVEL;

        return Component
                .text("Progress to Level " + NumberConverter.toRoman(nextLevel) + ":", NamedTextColor.WHITE)
                .appendSpace()
                .append(Component.text(Math.round(percent * 100) + "%", NamedTextColor.GOLD));
    }

    public static Component getProgressBar(long progress, SkillType skillType) {
        long nextProgress = LevelUtils.getProgressOfNextLevel(progress);

        double percent = 1 - (double) (nextProgress - progress) / LevelConstants.PROGRESS_PER_LEVEL;
        String progressString = StringUtils.getPart(IconConstants.PROGRESS_BAR, percent);

        ClickCallback<Audience> callback = audience -> {
            if (audience instanceof Player player) {
                DisplayProgressMenuEvent event = new DisplayProgressMenuEvent(player, skillType);
                Bukkit.getPluginManager().callEvent(event);
            }
        };

        ClickCallback.Options callbackOptions = ClickCallback.Options.builder()
                .uses(-1)
                .lifetime(Duration.ofMinutes(5))
                .build();

        return Component
                .text(progressString, NamedTextColor.GREEN)
                .append(
                        Component
                                .text(IconConstants.PROGRESS_BAR.substring(progressString.length()), NamedTextColor.WHITE)
                                .appendSpace()
                                .append(Component.text(progress % LevelConstants.PROGRESS_PER_LEVEL + "/" + LevelConstants.PROGRESS_PER_LEVEL, NamedTextColor.GOLD))
                )
                .clickEvent(ClickEvent.callback(callback, callbackOptions));
    }

    public static Component getSkillContent(String name, Skill skill) {
        SkillType skillType = skill.getType();
        long progress = skill.getProgress();

        NamedTextColor nameColor = SkillTypeConverter.toTextColor(skillType);

        // Delimiters
        Component openDelimiter = getDelimiterLine(name, nameColor);
        Component closeDelimiter = getDelimiterLine(null, null);

        Component resultComponent = openDelimiter;

        // Progress
        int nextLevel = skill.getLevel() + 1;
        if (nextLevel <= LevelConstants.MAX_LEVEL) {
            // Progress Bar
            resultComponent = resultComponent
                    .appendNewline()
                    .append(getProgressLevelString(nextLevel, progress))
                    .appendNewline()
                    .append(getProgressBar(progress, skillType))
                    .appendNewline();
        }

        resultComponent = resultComponent
                .appendNewline()
                .append(Component.text("Modifiers:", NamedTextColor.GREEN))
                .appendNewline();

        // Modifiers
        List<SkillModifier> modifiers = ModifierFactory.getModifiers(skillType);
        List<Component> components = new ArrayList<>();
        for (SkillModifier modifier : modifiers) {
            Component modifierContent = getModifierContent(modifier, skill.getLevel(), PREFIX);
            if (modifierContent == null)
                continue;

            components.add(modifierContent);
        }

        return resultComponent.append(components)
                .appendNewline()
                .append(closeDelimiter);
    }

    public static Component getModifierContent(SkillModifier modifier, int level) {
        return getModifierContent(modifier, level, PREFIX);
    }

    public static Component getModifierContent(SkillModifier modifier, int level, String prefix) {
        if (level < 0 || level > LevelConstants.MAX_LEVEL)
            return null;

        if (prefix == null)
            prefix = "";

        Map<Attribute, Double> prevAttributes = modifier.getAttributes(level - 1);
        Map<Attribute, Double> attributes = modifier.getAttributes(level);

        boolean hasModifiers = attributes.values()
                .stream()
                .anyMatch(s -> s > 0);

        if (!hasModifiers)
            return null;

        String name = modifier.getName();

        int modifiedLevel = modifier.getModifiedLevel(level);
        String romanLevel = NumberConverter.toRoman(modifiedLevel);

        // Name component: (ex.) Warrior IX
        NamedTextColor nameColor = NamedTextColor.GOLD;
        Component resultComponent = Component
                .text(prefix + name + " " + romanLevel)
                .color(TextColor.color(nameColor.asHSV()))
                .appendNewline();

        // Grants component: (ex.) Grants 4➜6% Health
        List<Component> grantComponents = new ArrayList<>();
        for (Attribute attribute : attributes.keySet()) {
            double prevScale = prevAttributes.get(attribute);
            double scale = attributes.get(attribute);

            if (scale == 0)
                continue;

            int oldPercent = NumberConverter.toPercent(prevScale);
            int newPercent = NumberConverter.toPercent(scale);
            String attributeName = AttributeConverter.toName(attribute);

            Component grantsComponent = Component
                    .text(prefix.repeat(2), NamedTextColor.WHITE)
                    .append(Component
                            .text("Grants ", NamedTextColor.WHITE)
                            .append(Component.text(oldPercent + "➜", NamedTextColor.DARK_GRAY))
                            .append(Component.text(newPercent + "%", NamedTextColor.GREEN))
                            .appendSpace()
                            .append(Component.text(attributeName, NamedTextColor.DARK_AQUA))
                    )
                    .appendNewline();

            grantComponents.add(grantsComponent);
        }

        return resultComponent.append(grantComponents);
    }
}
