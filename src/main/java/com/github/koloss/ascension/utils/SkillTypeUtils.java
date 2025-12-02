package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.model.SkillType;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class SkillTypeUtils {
    public static SkillType fromContainer(PersistentDataContainer container) {
        String sidebarValue = container.get(KeyConstants.SIDEBAR_KEY, PersistentDataType.STRING);
        return sidebarValue != null ? SkillType.valueOf(sidebarValue) : null;
    }

    public static NamedTextColor toTextColor(SkillType type) {
        switch (type) {
            case COMBAT -> {
                return NamedTextColor.RED;
            }
            case MINING -> {
                return NamedTextColor.BLUE;
            }
            case FARMING -> {
                return NamedTextColor.GREEN;
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
    }

    public static Material toMaterial(SkillType type) {
        switch (type) {
            case COMBAT -> {
                return Material.NETHERITE_SWORD;
            }
            case MINING -> {
                return Material.IRON_PICKAXE;
            }
            case FARMING -> {
                return Material.COPPER_HOE;
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
    }

    public static String toString(SkillType type) {
        switch (type) {
            case COMBAT -> {
                return "Combat";
            }
            case MINING -> {
                return "Mining";
            }
            case FARMING -> {
                return "Farming";
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
    }

    public static String toAbilityString(SkillType type) {
        switch (type) {
            case COMBAT -> {
                return "Warrior";
            }
            case MINING -> {
                return "Smith";
            }
            case FARMING -> {
                return "Sower";
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
    }

    public static String toDescription(SkillType type) {
        switch (type) {
            case COMBAT -> {
                return "Kill mobs and gain power";
            }
            case MINING -> {
                return "Mine ores and gain luck";
            }
            case FARMING -> {
                return "Seed or sow and gain health";
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
    }
}
