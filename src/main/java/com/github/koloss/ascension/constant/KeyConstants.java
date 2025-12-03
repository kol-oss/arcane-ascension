package com.github.koloss.ascension.constant;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;

import java.util.Map;

public final class KeyConstants {
    public static final String NAMESPACE = "ascension";

    public static final NamespacedKey MATERIAL_KEY = new NamespacedKey(NAMESPACE, "material");
    public static final NamespacedKey SIDEBAR_KEY = new NamespacedKey(NAMESPACE, "sidebar");

    private static final Map<Attribute, NamespacedKey> ATTRIBUTE_KEYS = Map.of(
            Attribute.ATTACK_DAMAGE, new NamespacedKey(NAMESPACE, "damage"),
            Attribute.LUCK, new NamespacedKey(NAMESPACE, "luck"),
            Attribute.MAX_HEALTH, new NamespacedKey(NAMESPACE, "health")
    );

    public static NamespacedKey from(Attribute attribute) {
        return ATTRIBUTE_KEYS.get(attribute);
    }
}
