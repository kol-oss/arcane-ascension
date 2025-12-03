package com.github.koloss.ascension.controller.modifier.impl;

import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.controller.modifier.ModifierManager;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.FormatUtils;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.utils.SkillTypeUtils;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ModifierManagerImpl implements ModifierManager {
    private SkillService skillService;

    @Override
    public void apply(Player player, SkillType skillType) {
        UUID userId = player.getUniqueId();
        Skill skill = skillService.findByUserIdAndType(userId, skillType);

        int level = skill.getLevel();
        int buff = LevelUtils.getBuffForLevel(level);
        double scalarBuff = (double) buff / 100;

        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_SCALAR;
        List<Attribute> attributes = SkillTypeUtils.toAttributes(skillType);

        for (Attribute attribute : attributes) {
            NamespacedKey key = KeyConstants.from(attribute);
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null)
                return;

            AttributeModifier existingModifier = attributeInstance.getModifier(key);
            if (existingModifier != null)
                attributeInstance.removeModifier(existingModifier);

            AttributeModifier modifier = new AttributeModifier(key, scalarBuff, operation);
            attributeInstance.addModifier(modifier);

            NamedTextColor color = SkillTypeUtils.toTextColor(skillType);
            Component message = Component
                    .text(FormatUtils.fromKey(key.getKey()), color)
                    .append(Component.text(" is increased by ", NamedTextColor.WHITE))
                    .append(Component.text(buff + "%", NamedTextColor.GREEN));

            player.sendMessage(message);
        }
    }
}