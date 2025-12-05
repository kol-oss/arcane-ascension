package com.github.koloss.ascension.controller.menu.icons;

import com.github.koloss.ascension.constant.WaypointConstants;
import com.github.koloss.ascension.model.Waypoint;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WaypointIconFactory {
    public static ItemStack createUsedWaypoint(Waypoint waypoint) {
        String locationContent = "(x: " + waypoint.getX() + ", y: " + waypoint.getY() + ", z: " + waypoint.getZ() + ")";

        return IconBuilder
                .of(waypoint.getName(), NamedTextColor.GOLD, Material.GREEN_BANNER)
                .lore(Component.text("This waypoint was set", NamedTextColor.GRAY))
                .lore(Component.empty())
                .lore(Component.text(locationContent, NamedTextColor.LIGHT_PURPLE))
                .build();
    }

    public static ItemStack createOpenWaypoint(int index, int totalLevel) {
        int expectedLevel = WaypointConstants.LEVEL_FOR_WAYPOINT * index;
        String levelContent = totalLevel + "/" + expectedLevel;

        return IconBuilder
                .of("Unlocked Waypoint", NamedTextColor.GOLD, Material.YELLOW_BANNER)
                .lore(Component.text("This waypoint is open", NamedTextColor.GRAY))
                .lore(Component.empty())
                .lore(Component
                        .text("Required level:", NamedTextColor.WHITE)
                        .appendSpace()
                        .append(Component.text(levelContent, NamedTextColor.GOLD))
                        .appendSpace()
                        .append(Component.text("(available)", NamedTextColor.GREEN))
                )
                .build();
    }

    public static ItemStack createLockedWaypoint(int index, int totalLevel) {
        int expectedLevel = WaypointConstants.LEVEL_FOR_WAYPOINT * index;

        int diffLevel = expectedLevel - totalLevel;
        String levelContent = totalLevel + "/" + expectedLevel;

        return IconBuilder
                .of("Locked Waypoint", NamedTextColor.GOLD, Material.LIGHT_GRAY_BANNER)
                .lore(Component.text("This waypoint is locked", NamedTextColor.GRAY))
                .lore(Component.empty())
                .lore(Component
                        .text("Required level:", NamedTextColor.WHITE)
                        .appendSpace()
                        .append(Component.text(levelContent, NamedTextColor.GOLD))
                        .appendSpace()
                        .append(Component.text("(" + diffLevel + " left)", NamedTextColor.RED))
                )
                .build();
    }
}
