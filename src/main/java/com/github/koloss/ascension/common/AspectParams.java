package com.github.koloss.ascension.common;

import com.github.koloss.ascension.model.DivineAspect;
import net.kyori.adventure.text.format.NamedTextColor;

public final class AspectParams {
    public static NamedTextColor getTextColor(DivineAspect aspect) {
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
}
