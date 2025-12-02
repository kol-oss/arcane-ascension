package com.github.koloss.ascension.common;

import com.github.koloss.ascension.model.DivineAspect;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;

public final class AspectParams {
    public static NamedTextColor toTextColor(DivineAspect aspect) {
        switch (aspect) {
            case WARDEN -> {
                return NamedTextColor.RED;
            }
            case SMITH -> {
                return NamedTextColor.BLUE;
            }
            case SOWER -> {
                return NamedTextColor.GREEN;
            }
            default -> throw new IllegalStateException("Unexpected aspect value: " + aspect);
        }
    }

    public static Material toMaterial(DivineAspect aspect) {
        switch (aspect) {
            case WARDEN -> {
                return Material.NETHERITE_SWORD;
            }
            case SMITH -> {
                return Material.IRON_PICKAXE;
            }
            case SOWER -> {
                return Material.COPPER_HOE;
            }
            default -> throw new IllegalStateException("Unexpected aspect value: " + aspect);
        }
    }

    public static String toString(DivineAspect aspect) {
        switch (aspect) {
            case WARDEN -> {
                return "Warden";
            }
            case SMITH -> {
                return "Smith";
            }
            case SOWER -> {
                return "Sower";
            }
            default -> throw new IllegalStateException("Unexpected aspect value: " + aspect);
        }
    }

    public static String toDescription(DivineAspect aspect) {
        switch (aspect) {
            case WARDEN -> {
                return "Kill angry mobs and gain power";
            }
            case SMITH -> {
                return "Mine ores and gain knowledge";
            }
            case SOWER -> {
                return "Seed or sow and gain life";
            }
            default -> throw new IllegalStateException("Unexpected aspect value: " + aspect);
        }
    }
}
