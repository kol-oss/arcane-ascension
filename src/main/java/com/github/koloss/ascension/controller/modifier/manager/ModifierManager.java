package com.github.koloss.ascension.controller.modifier.manager;

import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.controller.modifier.ModifierFactory;
import com.github.koloss.ascension.controller.modifier.SkillModifier;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModifierManager {
    private static ModifierManager manager;
    private final SkillService skillService;

    private ModifierManager(SkillService skillService) {
        this.skillService = skillService;
    }

    public static ModifierManager of(SkillService skillService) {
        if (manager == null)
            manager = new ModifierManager(skillService);

        return manager;
    }

    private void applyAttribute(Player player, Attribute attribute, double scale) {
        String keyValue = attribute.key().value();
        NamespacedKey key = new NamespacedKey(KeyConstants.NAMESPACE, keyValue);

        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance == null)
            return;

        AttributeModifier existingModifier = attributeInstance.getModifier(key);
        if (existingModifier != null)
            attributeInstance.removeModifier(existingModifier);

        AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_SCALAR;
        AttributeModifier modifier = new AttributeModifier(key, scale, operation);
        attributeInstance.addModifier(modifier);
    }

    public void apply(Player player, SkillType skillType) {
        UUID userId = player.getUniqueId();
        Skill skill = skillService.findByUserIdAndType(userId, skillType);

        List<SkillModifier> modifiers = ModifierFactory.getModifiers(skillType);
        for (SkillModifier modifier : modifiers) {
            Map<Attribute, Double> scales = modifier.getAttributes(skill.getLevel());
            Set<Attribute> attributes = scales.keySet();

            for (Attribute attribute : attributes) {
                Double value = scales.get(attribute);

                if (value == null || value == 0L)
                    continue;

                applyAttribute(player, attribute, value);
            }
        }
    }
}