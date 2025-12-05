package com.github.koloss.ascension.controller.menu.icons;

import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.utils.MessageUtils;
import com.github.koloss.ascension.utils.converter.NumberConverter;
import com.github.koloss.ascension.utils.converter.SkillTypeConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SkillIconFactory {
    public static ItemStack createFollowIcon(boolean isDisplayed) {
        String title = isDisplayed ? "Stop following" : "Follow";
        NamedTextColor titleColor = isDisplayed ? NamedTextColor.RED : NamedTextColor.GREEN;

        Material titleMaterial = isDisplayed ? Material.ENDER_EYE : Material.COMPASS;
        Component loreComponent = Component.text((isDisplayed ? "Hide" : "Display") + " this skill in sidebar", NamedTextColor.GRAY);

        return IconBuilder
                .of(title, titleColor, titleMaterial)
                .lore(loreComponent)
                .build();
    }

    public static ItemStack createLevelIcon(SkillType type, Material material, int level) {
        String levelString = NumberConverter.toRoman(level);

        String title = SkillTypeConverter.toString(type) + " Level " + levelString;
        NamedTextColor titleColor = SkillTypeConverter.toTextColor(type);

        Component rewardsComponent = Component.text("Rewards: ", NamedTextColor.GRAY);

        IconBuilder builder = IconBuilder
                .of(title, titleColor, material, level)
                .lore(rewardsComponent);

        List<SkillModifier> modifiers = ModifierFactory.getModifiers(type);
        for (SkillModifier modifier : modifiers) {
            Component modifierContent = MessageUtils.getModifierContent(modifier, level);
            if (modifierContent == null)
                continue;

            Component component = MessageUtils.formatToLore(modifierContent);
            List<Component> rows = component.children();

            builder.lore(component.children(List.of()));
            for (Component child : rows)
                builder.lore(child);
        }

        return builder.build();
    }
}
