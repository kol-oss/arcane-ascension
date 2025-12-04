package com.github.koloss.ascension.constant;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Map;

public final class ProgressConstants {
    public static final Map<EntityType, Long> PROGRESS_BY_KILL = Map.of(
            EntityType.ZOMBIE, 1L,
            EntityType.SPIDER, 1L,
            EntityType.SKELETON, 2L,
            EntityType.CREEPER, 2L,
            EntityType.WITCH, 3L,
            EntityType.ENDERMAN, 3L,
            EntityType.PLAYER, 5L
    );

    public static final Map<Material, Long> PROGRESS_BY_ORE = Map.of(
            Material.COAL_ORE, 1L,
            Material.COPPER_ORE, 1L,
            Material.IRON_ORE, 2L,
            Material.GOLD_ORE, 2L,
            Material.REDSTONE_ORE, 2L,
            Material.EMERALD_ORE, 3L,
            Material.DIAMOND_ORE, 3L,
            Material.NETHERITE_BLOCK, 5L
    );

    public static final Map<Material, Long> PROGRESS_BY_SEED = Map.of(
            Material.WHEAT_SEEDS, 1L,
            Material.CARROT, 1L,
            Material.POTATO, 1L,
            Material.BEETROOT_SEEDS, 2L,
            Material.MELON_SEEDS, 2L,
            Material.PUMPKIN_SEEDS, 2L
    );
}
